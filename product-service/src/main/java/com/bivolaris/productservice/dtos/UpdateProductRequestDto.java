package com.bivolaris.productservice.dtos;

import com.bivolaris.productservice.entities.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductRequestDto {
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String currency;
    private String categoryId;
    private String brand;
    private String model;
    private String sku;
    private ProductStatus status;
    private Integer weightGrams;
    private List<String> tags;
}
