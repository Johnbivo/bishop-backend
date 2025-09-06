package com.bivolaris.productservice.services.impl;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.dtos.ProductDto;
import com.bivolaris.productservice.dtos.ProductFilterDto;
import com.bivolaris.productservice.dtos.UpdateProductRequestDto;
import com.bivolaris.productservice.entities.Product;
import com.bivolaris.productservice.exceptions.ProductCreationException;
import com.bivolaris.productservice.exceptions.ProductNotFoundException;
import com.bivolaris.productservice.mappers.ProductMapper;
import com.bivolaris.productservice.repositories.ProductRepository;
import com.bivolaris.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable, ProductFilterDto filters) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (filters != null) {
            if (StringUtils.hasText(filters.getName())) {criteria.add(Criteria.where("name").regex(filters.getName(), "i"));}
            if (StringUtils.hasText(filters.getCategoryId())) {criteria.add(Criteria.where("categoryId").is(filters.getCategoryId()));}
            if (StringUtils.hasText(filters.getBrand())) {criteria.add(Criteria.where("brand").is(filters.getBrand()));}
            if (filters.getStatus() != null) {criteria.add(Criteria.where("status").is(filters.getStatus()));}
            if (filters.getMinPrice() != null) {criteria.add(Criteria.where("basePrice").gte(filters.getMinPrice()));}
            if (filters.getMaxPrice() != null) {criteria.add(Criteria.where("basePrice").lte(filters.getMaxPrice()));}
            if (StringUtils.hasText(filters.getTag())) {criteria.add(Criteria.where("tags").in(filters.getTag()));}
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);

        Query countQuery = new Query();
        if (!criteria.isEmpty()) {
            countQuery.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        long total = mongoTemplate.count(countQuery, Product.class);

        Page<Product> productsPage = new PageImpl<>(products, pageable, total);

        if (productsPage.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }

        return productsPage.map(productMapper::toDto);
    }

    @Override
    public ProductDto getProductById(String productId) {
        var product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found.");
        }
        return productMapper.toDto(product);
    }



    @Override
    public boolean createProduct(CreateProductRequestDto request){
        try{
            Product product = productMapper.toEntity(request);
            productRepository.save(product);
        }catch(Exception e){
            throw new ProductCreationException("Product could not be created.");
        }
        return true;

    }


    @Override
    public ProductDto updateProduct(UpdateProductRequestDto request, String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));
        Optional.ofNullable(request.getName()).ifPresent(product::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(request.getBasePrice()).ifPresent(product::setBasePrice);
        Optional.ofNullable(request.getCurrency()).ifPresent(product::setCurrency);
        Optional.ofNullable(request.getCategoryId()).ifPresent(product::setCategoryId);
        Optional.ofNullable(request.getBrand()).ifPresent(product::setBrand);
        Optional.ofNullable(request.getModel()).ifPresent(product::setModel);
        Optional.ofNullable(request.getSku()).ifPresent(product::setSku);
        Optional.ofNullable(request.getStatus()).ifPresent(product::setStatus);
        Optional.ofNullable(request.getWeightGrams()).ifPresent(product::setWeightGrams);
        Optional.ofNullable(request.getTags()).ifPresent(product::setTags);

        productRepository.save(product);

        return productMapper.toDto(product);
    }


    @Override
    public boolean deleteProduct(String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));
        productRepository.delete(product);
        return true;
    }


}
