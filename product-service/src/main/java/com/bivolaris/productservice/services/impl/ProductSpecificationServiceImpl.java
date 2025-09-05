package com.bivolaris.productservice.services.impl;


import com.bivolaris.productservice.dtos.CreateProductSpecificationRequestDto;
import com.bivolaris.productservice.dtos.ProductSpecificationDto;
import com.bivolaris.productservice.dtos.UpdateProductSpecificationRequestDto;
import com.bivolaris.productservice.exceptions.ProductSpecificationsCreationException;
import com.bivolaris.productservice.exceptions.ProductSpecificationsNotFoundException;
import com.bivolaris.productservice.mappers.ProductSpecificationMapper;
import com.bivolaris.productservice.repositories.ProductSpecificationRepository;
import com.bivolaris.productservice.services.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {


    private final ProductSpecificationRepository productSpecificationRepository;
    private final ProductSpecificationMapper productSpecificationMapper;


    @Override
    public List<ProductSpecificationDto> getAllProductSpecifications(){
        var productSpecs = productSpecificationRepository.findAll();
        if(productSpecs.isEmpty()){
            throw new ProductSpecificationsNotFoundException("Product specifications not found.");
        }
        return productSpecs.stream().map(productSpecificationMapper::toProductSpecificationDto).toList();
    }

    @Override
    public ProductSpecificationDto getProductSpecificationById(String productId){
        var productSpecification = productSpecificationRepository.findById(productId).orElseThrow(() -> new ProductSpecificationsNotFoundException("Product specification not found."));
        return productSpecificationMapper.toProductSpecificationDto(productSpecification);
    }

    @Override
    public boolean createProductSpecification(CreateProductSpecificationRequestDto request){
        try {
            var productSpecification = productSpecificationMapper.toProductSpecificationEntity(request);
            productSpecificationRepository.save(productSpecification);
        }catch (Exception e){
            throw new ProductSpecificationsCreationException("Product specification could not be created: " + e.getMessage());
        }

        return true;
    }

    @Override
    public boolean updateProductSpecification(UpdateProductSpecificationRequestDto request, String productId){
        try{
            var productSpecification = productSpecificationRepository.findById(productId).orElseThrow(() -> new ProductSpecificationsNotFoundException("Product specification not found."));

            Optional.ofNullable(request.getTechnicalSpecs()).ifPresent(productSpecification::setTechnicalSpecs);
            Optional.ofNullable(request.getFeatureSpecs()).ifPresent(productSpecification::setFeatureSpecs);

            productSpecificationRepository.save(productSpecification);
        }catch (Exception e){
            throw new ProductSpecificationsCreationException("Product specification could not be updated: " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean deleteProductSpecification(String productId){
        var productSpecification = productSpecificationRepository.findById(productId).orElseThrow(() -> new ProductSpecificationsNotFoundException("Product specification not found."));
        productSpecificationRepository.delete(productSpecification);
        return true;
    }
}
