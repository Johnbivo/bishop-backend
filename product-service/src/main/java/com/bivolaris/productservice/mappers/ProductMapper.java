package com.bivolaris.productservice.mappers;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.entities.Product;
import com.bivolaris.productservice.dtos.ProductDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product toEntity(CreateProductRequestDto createProductRequestDto);

    ProductDto toDto(Product product);


}
