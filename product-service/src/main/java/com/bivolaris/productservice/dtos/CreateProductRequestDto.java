package com.bivolaris.productservice.dtos;


import com.bivolaris.productservice.entities.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;



import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductRequestDto {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal basePrice;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

    private String brand;

    private String model;

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotNull(message = "Product status is required")
    private ProductStatus status;

    @PositiveOrZero(message = "Weight must be zero or positive")
    private Integer weightGrams;

    private List<String> tags;
}

