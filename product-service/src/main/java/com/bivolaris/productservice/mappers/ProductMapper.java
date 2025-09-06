package com.bivolaris.productservice.mappers;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.entities.Product;
import com.bivolaris.productservice.dtos.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product toEntity(CreateProductRequestDto createProductRequestDto);

    ProductDto toDto(Product product);


}
