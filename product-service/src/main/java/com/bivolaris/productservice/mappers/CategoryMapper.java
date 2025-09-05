package com.bivolaris.productservice.mappers;

import com.bivolaris.productservice.dtos.CategoryDto;
import com.bivolaris.productservice.dtos.CreateCategoryRequestDto;
import com.bivolaris.productservice.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);
    Category toCategoryEntity(CreateCategoryRequestDto createCategoryRequestDto);
}
