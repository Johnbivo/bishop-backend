package com.bivolaris.productservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequestDto {
    private String name;
    private String description;
    private String parentCategoryId;
    private Integer sortOrder;
    private Boolean isActive;
}

