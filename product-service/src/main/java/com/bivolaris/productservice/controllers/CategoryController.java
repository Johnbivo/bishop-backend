package com.bivolaris.productservice.controllers;


import com.bivolaris.productservice.dtos.CategoryDto;
import com.bivolaris.productservice.dtos.CreateCategoryRequestDto;
import com.bivolaris.productservice.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoriesService categoriesService;


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriesService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriesService.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CreateCategoryRequestDto request) {
        if(!categoriesService.createCategory(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(@RequestBody CreateCategoryRequestDto request, @PathVariable String categoryId) {
        if(!categoriesService.updateCategory(request, categoryId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        if(!categoriesService.deleteCategory(categoryId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
