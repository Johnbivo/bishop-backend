package com.bivolaris.productservice.services;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.dtos.ProductDto;
import com.bivolaris.productservice.dtos.UpdateProductRequestDto;

import java.util.List;

public interface ProductService {

    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(String id);
    public boolean createProduct(CreateProductRequestDto request);
    public ProductDto updateProduct(UpdateProductRequestDto request, String id);
    public boolean deleteProduct(String id);
}
