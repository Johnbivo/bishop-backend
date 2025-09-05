package com.bivolaris.productservice.dtos;


import lombok.Data;

@Data
public class UpdateInventoryRequestDto {

    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer reorderLevel;
}
