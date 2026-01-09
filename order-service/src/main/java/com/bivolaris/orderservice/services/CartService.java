package com.bivolaris.orderservice.services;


import com.bivolaris.orderservice.dtos.AddItemToCartDto;
import com.bivolaris.orderservice.dtos.CartDto;
import com.bivolaris.orderservice.dtos.CheckoutDto;
import com.bivolaris.orderservice.dtos.CreateCartRequestDto;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public List<CartDto> getAllCarts();
    public CartDto getCartById(UUID cartId);

    public CartDto getCartBySessionId(String sessionId);
    public CartDto createCart(String sessionId, CreateCartRequestDto createCartRequestDto);
    public CartDto addItemToCart(String sessionId, AddItemToCartDto addItemToCartDto);
    public Boolean removeItemFromCart(String sessionId, UUID productId);
    public Boolean cleanCart(String sessionId);
    Boolean checkout(String sessionId, CheckoutDto checkoutDto);
}
