package com.bivolaris.productservice.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("product_id")
    private String productId =  UUID.randomUUID().toString();

    @Indexed
    private String name;

    private String description;

    @Field("base_price")
    private BigDecimal basePrice;

    private String currency;

    @Indexed
    @Field("category_id")
    private String categoryId;

    private String brand;
    private String model;

    @Indexed(unique = true)
    private String sku;


    @Indexed
    private ProductStatus status;

    @Field("weight_grams")
    private Integer weightGrams;

    private List<String> tags = new ArrayList<>();


    @Field("image_urls")
    private List<String> imageUrls = new ArrayList<>();


    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;


    @Version
    private Integer version;

}

