package com.bivolaris.productservice.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "inventory")
public class Inventory {
    @Id
    private String id;

    @Indexed
    @Field("product_id")
    private String productId = UUID.randomUUID().toString(); // Links to Product or ProductVariant

    @Field("available_quantity")
    private Integer availableQuantity;

    @Field("reserved_quantity")
    private Integer reservedQuantity; // Reserved for pending orders

    @Field("total_quantity")
    private Integer totalQuantity; // available + reserved

    @Field("reorder_level")
    private Integer reorderLevel; // Minimum stock level

    @LastModifiedDate
    @Field("last_updated")
    private LocalDateTime lastUpdated;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("last_restocked")
    private LocalDateTime lastRestocked;

    }