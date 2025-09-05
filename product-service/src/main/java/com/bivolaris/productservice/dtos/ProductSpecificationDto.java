package com.bivolaris.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ProductSpecificationDto {

    private String id;
    private String productId;
    private Map<String, Object> technicalSpecs;
    private Map<String, Boolean> featureSpecs;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
