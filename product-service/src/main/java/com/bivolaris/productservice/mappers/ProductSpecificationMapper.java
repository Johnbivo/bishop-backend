package com.bivolaris.productservice.mappers;


import com.bivolaris.productservice.dtos.CreateProductSpecificationRequestDto;
import com.bivolaris.productservice.dtos.ProductSpecificationDto;
import com.bivolaris.productservice.entities.ProductSpecification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductSpecificationMapper {

    ProductSpecificationDto toProductSpecificationDto(ProductSpecification productSpecification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductSpecification toProductSpecificationEntity(CreateProductSpecificationRequestDto createProductSpecificationRequestDto);
}
