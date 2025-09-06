package com.bivolaris.orderservice.services.impl;

import com.bivolaris.orderservice.dtos.AddItemToCartDto;
import com.bivolaris.orderservice.dtos.CartDto;
import com.bivolaris.orderservice.dtos.CreateCartRequestDto;
import com.bivolaris.orderservice.entities.Cart;
import com.bivolaris.orderservice.entities.CartItem;
import com.bivolaris.orderservice.entities.CurrencyEnum;
import com.bivolaris.orderservice.exceptions.CartItemNotFoundException;
import com.bivolaris.orderservice.exceptions.CartNotFoundException;
import com.bivolaris.orderservice.exceptions.ProductNotFoundException;
import com.bivolaris.orderservice.grpc.ProductServiceGrpcClient;
import com.bivolaris.orderservice.mappers.CartMapper;
import com.bivolaris.orderservice.repositories.CartItemRepository;
import com.bivolaris.orderservice.repositories.CartRepository;
import com.bivolaris.orderservice.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.GetStockResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductServiceGrpcClient productServiceGrpcClient;
    private final CartItemRepository cartItemRepository;


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

    @Transactional
    @Override
    public CartDto createCart(CreateCartRequestDto createCartRequestDto){

        if(createCartRequestDto == null) {
            createCartRequestDto = new CreateCartRequestDto();
        }

        var cart = cartMapper.toCartEntity(createCartRequestDto);
        if(cart == null){
            throw new CartNotFoundException("Cart not found.");
        }
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);

    }

    @Transactional
    @Override
    public CartDto addItemToCart(UUID cartId, AddItemToCartDto addItemToCartDto) {

        var cart = cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = cartMapper.toCartEntity(new CreateCartRequestDto());
            cartRepository.save(newCart);
            return newCart;
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
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                cartItemRepository.save(existingCartItem);
                return cartMapper.toCartDto(cart);
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

                return cartMapper.toCartDto(cart);
            }

        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item to cart: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Boolean removeItemFromCart(UUID cartId, UUID productId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found."));

        var cartItem = cartItemRepository.findByCartAndProductId(cart, productId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found."));


        cartItemRepository.delete(cartItem);

        return true;
    }


    @Override
    public Boolean cleanCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found."));
        cartItemRepository.deleteByCart(cart);
        return  true;
    }

    @Override
    public Boolean deleteCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found."));
        cartRepository.delete(cart);
        return true;

    }

    @Override
    public Boolean checkout(UUID cartId){
        var cart =  cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found."));

        var cartItems = cartItemRepository.findAllByCart(cart);
        if(cartItems.isEmpty()){
            throw new CartItemNotFoundException("Cart items not found in cart.");
        }


        // TODO: Remove for loop and replace the request and response of the proto file with
        //  a list.
        // For now I will do a for loop to check stock in the product service
        // This needs to change to one time list request and response.
        for (var item : cartItems) {
            UUID productId = item.getProductId();
            GetStockResponse stockResponse = productServiceGrpcClient.getStockLevel(productId);

            if (stockResponse.getAvailableQuantity() < item.getQuantity()) {
                throw new IllegalStateException(
                        "Not enough stock for product: " + item.getProductName()
                );
            }
        }


        //TODO: CONTINUE TO CREATE AN ORDER
        return  true;


    }
}
