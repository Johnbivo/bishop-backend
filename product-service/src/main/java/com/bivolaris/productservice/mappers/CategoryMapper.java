package com.bivolaris.productservice.mappers;

import com.bivolaris.productservice.dtos.CategoryDto;
import com.bivolaris.productservice.dtos.CreateCategoryRequestDto;
import com.bivolaris.productservice.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "categoryPath", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category toCategoryEntity(CreateCategoryRequestDto createCategoryRequestDto);
}
