package com.bivolaris.productservice.services.impl;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.dtos.ProductDto;
import com.bivolaris.productservice.dtos.UpdateProductRequestDto;
import com.bivolaris.productservice.entities.Product;
import com.bivolaris.productservice.exceptions.ProductCreationException;
import com.bivolaris.productservice.exceptions.ProductNotFoundException;
import com.bivolaris.productservice.mappers.ProductMapper;
import com.bivolaris.productservice.repositories.ProductRepository;
import com.bivolaris.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public List<ProductDto> getAllProducts(){
        var products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found.");
        }
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));
        return productMapper.toDto(product);
    }



    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public boolean deleteProduct(String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));
        productRepository.delete(product);
        return true;
    }


}
