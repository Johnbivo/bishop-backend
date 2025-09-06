package com.bivolaris.orderservice.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemDto {

    private UUID productId;
    private String productName;
    private String productBrand;
    private String productModel;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
}
