package com.bivolaris.productservice.mappers;


import com.bivolaris.productservice.dtos.CreateProductSpecificationRequestDto;
import com.bivolaris.productservice.dtos.ProductSpecificationDto;
import com.bivolaris.productservice.entities.ProductSpecification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSpecificationMapper {

    ProductSpecificationDto toProductSpecificationDto(ProductSpecification productSpecification);

    ProductSpecification toProductSpecificationEntity(CreateProductSpecificationRequestDto createProductSpecificationRequestDto);
}
