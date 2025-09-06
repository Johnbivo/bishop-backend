package com.bivolaris.productservice.services.impl;

import com.bivolaris.productservice.dtos.CategoryDto;
import com.bivolaris.productservice.dtos.CreateCategoryRequestDto;
import com.bivolaris.productservice.exceptions.CategoryCreationException;
import com.bivolaris.productservice.exceptions.CategoryNotFoundException;
import com.bivolaris.productservice.mappers.CategoryMapper;
import com.bivolaris.productservice.repositories.CategoryRepository;
import com.bivolaris.productservice.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoriesService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public List<CategoryDto> getAllCategories(){
        var categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new CategoryNotFoundException("No categories found.");
        }
        return categories.stream().map(categoryMapper::toCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(String categoryId){
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName){
        var category = categoryRepository.findByName(categoryName).orElseThrow(() -> new CategoryNotFoundException(categoryName));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public boolean createCategory(CreateCategoryRequestDto request){
        try {
            var category = categoryMapper.toCategoryEntity(request);
            categoryRepository.save(category);
        }catch (Exception e){
            throw new CategoryCreationException("Could not create product: " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean updateCategory(CreateCategoryRequestDto request, String categoryId){
        try {
            var category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

            Optional.ofNullable(request.getName()).ifPresent(category::setName);
            Optional.ofNullable(request.getDescription()).ifPresent(category::setDescription);
            Optional.ofNullable(request.getParentCategoryId()).ifPresent(category::setParentCategoryId);
            Optional.ofNullable(request.getSortOrder()).ifPresent(category::setSortOrder);
            Optional.ofNullable(request.getIsActive()).ifPresent(category::setActive);

            categoryRepository.save(category);
        }catch (Exception e){
            throw new CategoryCreationException("Could not update product: " + e.getMessage());
        }
        return true;

    }

    @Override
    public boolean deleteCategory(String categoryId){
        var  category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        categoryRepository.delete(category);
        return true;
    }
}
