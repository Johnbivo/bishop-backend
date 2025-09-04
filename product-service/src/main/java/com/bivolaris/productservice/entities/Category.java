package com.bivolaris.productservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "categories")
public class Category {
    @Id
    private String id;

    @Indexed(unique = true)
    @Field("category_id")
    private String categoryId = UUID.randomUUID().toString();

    @Indexed
    private String name;

    private String description;

    @Field("parent_category_id")
    private String parentCategoryId; // For hierarchical categories: Electronics > Audio > Headphones

    @Field("category_path")
    private String categoryPath; // "/electronics/audio/headphones"

    @Field("sort_order")
    private Integer sortOrder;

    @Field("is_active")
    private boolean isActive;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;


}