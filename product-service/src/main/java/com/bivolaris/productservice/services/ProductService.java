package com.bivolaris.productservice.services;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.dtos.ProductDto;
import com.bivolaris.productservice.dtos.ProductFilterDto;
import com.bivolaris.productservice.dtos.UpdateProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    public Page<ProductDto> getAllProducts(Pageable pageable, ProductFilterDto filters);
    public ProductDto getProductById(String id);
    public boolean createProduct(CreateProductRequestDto request);
    public ProductDto updateProduct(UpdateProductRequestDto request, String id);
    public boolean deleteProduct(String id);
}
