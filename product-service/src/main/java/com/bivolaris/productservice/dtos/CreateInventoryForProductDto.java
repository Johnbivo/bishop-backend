package com.bivolaris.productservice.dtos;


import lombok.Data;

@Data
public class CreateInventoryForProductDto {

    private String productId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer reorderLevel;

}
