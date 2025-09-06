package com.bivolaris.productservice.repositories;

import com.bivolaris.productservice.dtos.ProductFilterDto;
import com.bivolaris.productservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByProductId(String productId);
}
