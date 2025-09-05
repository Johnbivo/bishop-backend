package com.bivolaris.productservice.services;

import com.bivolaris.productservice.dtos.CreateProductSpecificationRequestDto;
import com.bivolaris.productservice.dtos.ProductSpecificationDto;
import com.bivolaris.productservice.dtos.UpdateProductSpecificationRequestDto;


import java.util.List;

public interface ProductSpecificationService {

    public List<ProductSpecificationDto> getAllProductSpecifications();
    public ProductSpecificationDto getProductSpecificationById(String productId);
    public boolean createProductSpecification(CreateProductSpecificationRequestDto createProductSpecificationRequestDto);
    public boolean updateProductSpecification(UpdateProductSpecificationRequestDto updateProductSpecificationRequestDto, String productId);
    public boolean deleteProductSpecification(String productId);
}
