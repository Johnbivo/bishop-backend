package com.bivolaris.productservice.services;

import com.bivolaris.productservice.dtos.CategoryDto;
import com.bivolaris.productservice.dtos.CreateCategoryRequestDto;

import java.util.List;

public interface CategoriesService {

    public List<CategoryDto> getAllCategories();
    public CategoryDto getCategoryById(String categoryId);
    public CategoryDto getCategoryByName(String categoryName);
    public boolean createCategory(CreateCategoryRequestDto request);
    public boolean updateCategory(CreateCategoryRequestDto request, String categoryId);
    public boolean deleteCategory(String categoryId);

}
