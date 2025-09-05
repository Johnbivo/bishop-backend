package com.bivolaris.productservice.controllers;

import com.bivolaris.productservice.dtos.CreateProductRequestDto;
import com.bivolaris.productservice.dtos.ProductDto;
import com.bivolaris.productservice.dtos.ProductFilterDto;
import com.bivolaris.productservice.dtos.UpdateProductRequestDto;
import com.bivolaris.productservice.entities.ProductStatus;
import com.bivolaris.productservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;


    @Operation(summary = "Get all products.", description = "Returns all products of the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No products found.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String tag) {

        ProductFilterDto filters = new ProductFilterDto();
        filters.setName(name);
        filters.setCategoryId(categoryId);
        filters.setBrand(brand);
        filters.setStatus(status);
        filters.setMinPrice(minPrice);
        filters.setMaxPrice(maxPrice);
        filters.setTag(tag);

        return ResponseEntity.ok(productService.getAllProducts(pageable, filters));
    }





    @Operation(summary = "Get a product.", description = "Returns the product with the provided id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }


    @Operation(summary = "Create a new product.", description = "Creates a product with the given body.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request data. Product could not be created.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductRequestDto request) {
        if(!productService.createProduct(request)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Update/Modify a product", description = "Updates the product with the given body and id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data (body or id). Product could not be updated.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody UpdateProductRequestDto request, @PathVariable String id) {
        var productDto = productService.updateProduct(request,id);
        if(productDto!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }


    @Operation(summary = "Delete a product.", description = "Delete a product with the given id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid id. Product could not be deleted.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if(!productService.deleteProduct(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
