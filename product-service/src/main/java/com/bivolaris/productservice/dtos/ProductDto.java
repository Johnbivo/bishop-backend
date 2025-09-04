package com.bivolaris.productservice.dtos;

import com.bivolaris.productservice.entities.ProductStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


@Data
public class ProductDto{
    String productId;
    String name;
    String description;
    BigDecimal basePrice;
    String currency;
    String categoryId;
    String brand;
    String model;
    String sku;
    ProductStatus status;
    Integer weightGrams;
    List<String> tags;
}