package com.bivolaris.productservice.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {

    private String categoryId;
    private String name;
    private String description;
    private String parentCategoryId;
    private String categoryPath;
    private Integer sortOrder;
    private boolean isActive;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
