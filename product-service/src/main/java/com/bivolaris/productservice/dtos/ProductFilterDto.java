package com.bivolaris.productservice.dtos;

import com.bivolaris.productservice.entities.ProductStatus;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductFilterDto {
    private String name;
    private String categoryId;
    private String brand;
    private ProductStatus status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String tag;
}