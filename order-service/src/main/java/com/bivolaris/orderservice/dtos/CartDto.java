package com.bivolaris.orderservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {

    private UUID id;
    private UUID userId;
    private String sessionId;
    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime expiresAt;

    private List<CartItemDto> cartItems;


}
