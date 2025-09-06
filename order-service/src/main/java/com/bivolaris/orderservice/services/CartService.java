package com.bivolaris.orderservice.services;


import com.bivolaris.orderservice.dtos.AddItemToCartDto;
import com.bivolaris.orderservice.dtos.CartDto;
import com.bivolaris.orderservice.dtos.CreateCartRequestDto;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public List<CartDto> getAllCarts();
    public CartDto getCartById(UUID cartId);
    public CartDto createCart(CreateCartRequestDto createCartRequestDto);
    public CartDto addItemToCart(UUID cartId, AddItemToCartDto addItemToCartDto);
    public Boolean removeItemFromCart(UUID cartId, UUID productId);
    public Boolean cleanCart(UUID cartId);
    public Boolean deleteCart(UUID cartId);
    public Boolean checkout(UUID cartId);
}
