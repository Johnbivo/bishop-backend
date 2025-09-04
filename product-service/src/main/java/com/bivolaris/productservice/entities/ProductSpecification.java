package com.bivolaris.productservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "product_specifications")
public class ProductSpecification {
    @Id
    private String id;

    @Indexed
    @Field("product_id")
    private String productId  = UUID.randomUUID().toString(); // Can reference Product or ProductVariant

    // General specifications
    @Field("technical_specs")
    private Map<String, Object> technicalSpecs; // {"battery_life": "30 hours", "connectivity": ["Bluetooth 5.0", "USB-C"]}

    @Field("feature_specs")
    private Map<String, Boolean> featureSpecs; // {"waterproof": true, "wireless_charging": false}


    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
