package com.bivolaris.productservice.controllers;

import com.bivolaris.productservice.dtos.CreateProductSpecificationRequestDto;
import com.bivolaris.productservice.dtos.ProductSpecificationDto;
import com.bivolaris.productservice.dtos.UpdateProductSpecificationRequestDto;
import com.bivolaris.productservice.services.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products/product-specifications")
public class ProductSpecificationsController {

    private final ProductSpecificationService productSpecificationService;

    @GetMapping
    public ResponseEntity<List<ProductSpecificationDto>> getAllProductSpecifications(){
        return ResponseEntity.status(HttpStatus.OK).body(productSpecificationService.getAllProductSpecifications());

    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductSpecificationDto> getProductSpecificationById(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.OK).body(productSpecificationService.getProductSpecificationById(productId));
    }

    @PostMapping
    public ResponseEntity<Void> createProductSpecification(@RequestBody CreateProductSpecificationRequestDto request){
        if(!productSpecificationService.createProductSpecification(request)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProductSpecification(@RequestBody UpdateProductSpecificationRequestDto request, @PathVariable String productId){
        if(!productSpecificationService.updateProductSpecification(request,productId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductSpecification(@PathVariable String productId){
        if(!productSpecificationService.deleteProductSpecification(productId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
