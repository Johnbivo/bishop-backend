package com.bivolaris.productservice.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateProductSpecificationRequestDto {

    private Map<String, Object> technicalSpecs;
    private Map<String, Boolean> featureSpecs;
}
