package com.bivolaris.orderservice.controllers;


import com.bivolaris.orderservice.dtos.AddItemToCartDto;
import com.bivolaris.orderservice.dtos.CartDto;
import com.bivolaris.orderservice.dtos.CreateCartRequestDto;
import com.bivolaris.orderservice.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;


    @Operation(summary = "Returns all carts.", description = "Returns all carts.")
    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getAllCarts());
    }


    @Operation(summary = "Returns a cart.",description = "Returns a cart with the give cartId.")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable UUID cartId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartById(cartId));
    }

    @Operation(summary = "Create a cart.", description = "Creates a cart with the given " +
            "createCartRequestDto, if not provided, it creates a cart automatically with " +
            "no user session info.")
    @PostMapping
    public ResponseEntity<CartDto> createCart(@Valid @RequestBody CreateCartRequestDto createCartRequestDto) {
         return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(createCartRequestDto));
    }

    @Operation(summary = "Add a product to a cart.", description = "Adds the given product to the given cart id.")
    @PostMapping("/{cartId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable UUID cartId, AddItemToCartDto  addItemToCartDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addItemToCart(cartId, addItemToCartDto));
    }

    @Operation(summary = "Remove a product from a cart.", description = "Removes a product from a cart based on ids.")
    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable UUID cartId,@PathVariable UUID productId) {
        var operation = cartService.removeItemFromCart(cartId, productId);
        if(!operation) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
