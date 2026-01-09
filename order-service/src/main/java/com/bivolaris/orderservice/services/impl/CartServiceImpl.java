package com.bivolaris.orderservice.services.impl;

import com.bivolaris.orderservice.dtos.AddItemToCartDto;
import com.bivolaris.orderservice.dtos.CartDto;
import com.bivolaris.orderservice.dtos.CheckoutDto;
import com.bivolaris.orderservice.dtos.CreateCartRequestDto;
import com.bivolaris.orderservice.entities.*;
import com.bivolaris.orderservice.exceptions.CartItemNotFoundException;
import com.bivolaris.orderservice.exceptions.CartNotFoundException;
import com.bivolaris.orderservice.exceptions.ProductNotFoundException;
import com.bivolaris.orderservice.grpc.ProductServiceGrpcClient;
import com.bivolaris.orderservice.grpc.UserServiceGrpcClient;
import com.bivolaris.orderservice.mappers.CartMapper;
import com.bivolaris.orderservice.repositories.*;
import com.bivolaris.orderservice.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.GetStockForAllResponse;
import products.GetStockResponse;
import org.springframework.cache.annotation.Cacheable;
import userServiceGrpc.GetUserResponse;
import userServiceGrpc.UserAddressMessage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductServiceGrpcClient productServiceGrpcClient;
    private final CartItemRepository cartItemRepository;
    private final RedisTemplate<String, CartDto> redisTemplate;


    private final UserServiceGrpcClient userServiceGrpcClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;




    @Override
    public List<CartDto> getAllCarts(){
        var cart = cartRepository.findAll();
        if(cart.isEmpty()){
            throw new CartNotFoundException("No carts found.");
        }
        return cart.stream().map(cartMapper::toCartDto).toList();
    }

    @Override
    public CartDto getCartById(UUID cartId){
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found."));
        return cartMapper.toCartDto(cart);
    }

    // Add these methods to your CartServiceImpl class

    @Override
    @Cacheable(value = "carts", key = "#sessionId")
    public CartDto getCartBySessionId(String sessionId) {
        var cart = cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> {

                    Cart newCart = new Cart();
                    newCart.setSessionId(sessionId);
                    return cartRepository.save(newCart);
                });
        return cartMapper.toCartDto(cart);
    }

    @Transactional
    @Override
    public CartDto createCart(String sessionId, CreateCartRequestDto createCartRequestDto) {
        if (createCartRequestDto == null) {
            createCartRequestDto = new CreateCartRequestDto();
        }

        var cart = cartMapper.toCartEntity(createCartRequestDto);
        cart.setSessionId(sessionId);

        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    @Transactional
    @Override
    @CachePut(value = "carts", key = "#sessionId")
    public CartDto addItemToCart(String sessionId, AddItemToCartDto addItemToCartDto) {
        var cart = cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setSessionId(sessionId);
                    return cartRepository.save(newCart);
                });

        try {
            var productId = addItemToCartDto.getProductId();
            var product = productServiceGrpcClient.getProductById(productId);

            if (product == null) {
                throw new ProductNotFoundException("Product not found for ID: " + productId);
            }

            // If a product already exists just increment the quantity
            var existingCartItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
            if (existingCartItemOpt.isPresent()) {
                CartItem existingCartItem = existingCartItemOpt.get();
                existingCartItem.setQuantity(existingCartItem.getQuantity() + addItemToCartDto.getQuantity());
                cartItemRepository.save(existingCartItem);
            } else {
                // If its a new product create it and add it.
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProductId(productId);
                cartItem.setQuantity(addItemToCartDto.getQuantity());
                cartItem.setProductName(product.getName());
                cartItem.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
                cartItem.setCurrency(CurrencyEnum.valueOf(product.getCurrency()));
                cartItem.setProductBrand(product.getBrand());
                cartItem.setProductModel(product.getModel());

                cartItemRepository.save(cartItem);
            }

            return cartMapper.toCartDto(cart);

        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product not found: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item to cart: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Boolean removeItemFromCart(String sessionId, UUID productId) {
        var cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for session: " + sessionId));

        var cartItem = cartItemRepository.findByCartAndProductId(cart, productId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found."));

        cartItemRepository.delete(cartItem);
        return true;
    }

    @Override
    @CacheEvict(value = "carts", key = "#sessionId")
    public Boolean cleanCart(String sessionId) {
        var cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for session: " + sessionId));

        cartItemRepository.deleteByCart(cart);
        return true;
    }

    @Override
    @CacheEvict(value = "carts", key = "#sessionId")
    public Boolean checkout(String sessionId, CheckoutDto checkoutDto) {
        var cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for session: " + sessionId));

        var cartItems = cartItemRepository.findAllByCart(cart);
        if (cartItems.isEmpty()) {
            throw new CartItemNotFoundException("Cart items not found in cart.");
        }

        // Getting all the items in the cart
        List<UUID> productIds = cartItems.stream()
                .map(CartItem::getProductId)
                .toList();

        // Call the product service and get the stock
        GetStockForAllResponse stockResponse = productServiceGrpcClient.getStockLevelForAll(productIds);

        // productId : {stock}
        Map<String, Integer> stockMap = stockResponse.getStocksList().stream()
                .collect(Collectors.toMap(GetStockResponse::getProductId, GetStockResponse::getAvailableQuantity));

        // Check each stock
        for (var item : cartItems) {
            int available = stockMap.getOrDefault(item.getProductId().toString(), 0);
            if (available < item.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + item.getProductName());
            }
        }

        Order order = new Order();

        if (cart.getUserId() != null) {
            // LOGGED-IN USER: Get user info via gRPC (checkoutDto will be null)

            GetUserResponse userResponse = userServiceGrpcClient.getUserWithAddresses(cart.getUserId().toString());


            UserAddressMessage billingAddress = userResponse.getAddressesList().stream()
                    .filter(addr -> {
                        String type = addr.getType();
                        return (type.equals("BILLING") || type.equals("BOTH")) && addr.getIsDefault();
                    })
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No default billing address found for user"));


            UserAddressMessage shippingAddress = userResponse.getAddressesList().stream()
                    .filter(addr -> {
                        String type = addr.getType();
                        return (type.equals("SHIPPING") || type.equals("BOTH")) && addr.getIsDefault();
                    })
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No default shipping address found for user"));

            order.setUserId(cart.getUserId());
            order.setSessionId(UUID.fromString(sessionId));

            // Set billing address from UserService
            order.setBillingAddressLine1(billingAddress.getAddressLine1());
            order.setBillingAddressLine2(billingAddress.getAddressLine2());
            order.setBillingCity(billingAddress.getCity());
            order.setBillingState(billingAddress.getState());
            order.setBillingCountry(billingAddress.getCountry());

            // Set billing account from UserService response
            if (!userResponse.getBillingAccountId().isEmpty()) {
                order.setBillingAccountId(UUID.fromString(userResponse.getBillingAccountId()));
            }

            // Set shipping address from UserService
            order.setShippingAddressLine1(shippingAddress.getAddressLine1());
            order.setShippingAddressLine2(shippingAddress.getAddressLine2());
            order.setShippingCity(shippingAddress.getCity());
            order.setShippingState(shippingAddress.getState());
            order.setShippingCountry(shippingAddress.getCountry());

            // Set currency from cart items
            if (!cartItems.isEmpty()) {
                order.setCurrency(cartItems.getFirst().getCurrency());
            }

            order.setOrderStatus(OrderStatusEnum.CONFIRMED);

        } else {
            // GUEST USER: Use data from checkoutDto
            if (checkoutDto == null) {
                throw new IllegalStateException("Checkout information required for guest users");
            }

            order.setSessionId(UUID.fromString(sessionId));

            // Set billing address from DTO
            order.setBillingAddressLine1(checkoutDto.getBillingAddressLine1());
            order.setBillingAddressLine2(checkoutDto.getBillingAddressLine2());
            order.setBillingCity(checkoutDto.getBillingCity());
            order.setBillingState(checkoutDto.getBillingState());
            order.setBillingCountry(checkoutDto.getBillingCountry());

            // Set shipping address from DTO
            order.setShippingAddressLine1(checkoutDto.getShippingAddressLine1());
            order.setShippingAddressLine2(checkoutDto.getShippingAddressLine2());
            order.setShippingCity(checkoutDto.getShippingCity());
            order.setShippingState(checkoutDto.getShippingState());
            order.setShippingCountry(checkoutDto.getShippingCountry());

            // Set payment method if provided
            if (checkoutDto.getPaymentMethodId() != null && !checkoutDto.getPaymentMethodId().isEmpty()) {
                order.setPaymentMethodId(UUID.fromString(checkoutDto.getPaymentMethodId()));
            }

            // Set currency from cart items
            if (!cartItems.isEmpty()) {
                order.setCurrency(cartItems.getFirst().getCurrency());
            }

            order.setOrderStatus(OrderStatusEnum.CONFIRMED);
        }




        order.setTotalAmount(cart.getTotalAmount());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());


        Order savedOrder = orderRepository.save(order);

        // Create order items from cart items
        cartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setProductSku(cartItem.getProductModel());
            orderItem.setCreatedAt(LocalDateTime.now());

            if (savedOrder.getOrderItems() == null) {
                savedOrder.setOrderItems(new ArrayList<>());
            }
            savedOrder.getOrderItems().add(orderItem);
        });

        orderRepository.save(savedOrder);

        // Create initial status history entry
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setOrder(savedOrder);
        statusHistory.setOldStatus(null);
        statusHistory.setNewStatus(savedOrder.getOrderStatus());
        statusHistory.setChangedAt(LocalDateTime.now());
        statusHistory.setNotes("Order created");
        orderStatusHistoryRepository.save(statusHistory);

        // TODO: Reduce stock levels via gRPC call to ProductService
        // TODO: Clear cart after successful order (call to billing service)

        return true;
    }





}
