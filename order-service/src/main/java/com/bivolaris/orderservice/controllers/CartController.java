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
@RequestMapping("/orders/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Returns all carts.", description = "Returns all carts.")
    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getAllCarts());
    }

    @Operation(summary = "Get current user's cart.", description = "Returns the cart for the current session.")
    @GetMapping("/current")
    public ResponseEntity<CartDto> getCurrentCart(@RequestHeader("session-id") String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartBySessionId(sessionId));
    }

    @Operation(summary = "Returns a cart.", description = "Returns a cart with the given cartId.")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable UUID cartId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartById(cartId));
    }

    @Operation(summary = "Create a cart.", description = "Creates a cart for the current session.")
    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestHeader("session-id") String sessionId,
                                              @Valid @RequestBody(required = false) CreateCartRequestDto createCartRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(sessionId, createCartRequestDto));
    }

    @Operation(summary = "Add a product to current cart.", description = "Adds the given product to the current session's cart.")
    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCurrentCart(@RequestHeader("session-id") String sessionId,
                                                        @Valid @RequestBody AddItemToCartDto addItemToCartDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addItemToCart(sessionId, addItemToCartDto));
    }

    @Operation(summary = "Add a product to a cart.", description = "Adds the given product to the given cart id.")
    @PostMapping("/{cartId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable UUID cartId,
                                                 @Valid @RequestBody AddItemToCartDto addItemToCartDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addItemToCart(String.valueOf(cartId), addItemToCartDto));
    }

    @Operation(summary = "Remove a product from current cart.", description = "Removes a product from current session's cart.")
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItemFromCurrentCart(@RequestHeader("session-id") String sessionId,
                                                          @PathVariable UUID productId) {
        boolean operation = cartService.removeItemFromCart(sessionId, productId);
        if (!operation) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Remove a product from a cart.", description = "Removes a product from a cart based on ids.")
    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable UUID cartId, @PathVariable UUID productId) {
        boolean operation = cartService.removeItemFromCart(String.valueOf(cartId), productId);
        if (!operation) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}