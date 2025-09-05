package com.bivolaris.productservice.dtos;


import lombok.Data;

import java.util.Map;

@Data
public class CreateProductSpecificationRequestDto {

    private String productId;
    private Map<String, Object> technicalSpecs;
    private Map<String, Boolean> featureSpecs;
}
