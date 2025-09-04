package com.bivolaris.productservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecification extends MongoRepository<ProductSpecification, String> {
}
