package com.bivolaris.productservice.repositories;

import com.bivolaris.productservice.entities.ProductSpecification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecificationRepository extends MongoRepository<ProductSpecification, String> {
}
