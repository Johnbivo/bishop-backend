package com.bivolaris.productservice.controllers;

import com.bivolaris.productservice.dtos.CreateInventoryForProductDto;
import com.bivolaris.productservice.dtos.InventoryDto;
import com.bivolaris.productservice.dtos.UpdateInventoryRequestDto;
import com.bivolaris.productservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products/inventory")
public class InventoryController {


    private final InventoryService inventoryService;


    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventory(){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllInventory());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<Void> createInventoryForProduct(@RequestBody CreateInventoryForProductDto request){
        if(!inventoryService.createInventoryForProduct(request)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateInvetoryForProduct(@RequestBody UpdateInventoryRequestDto request, @PathVariable String productId){
        if(!inventoryService.updateInventoryForProduct(request,productId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventoryForProduct(@PathVariable String productId){
        if(!inventoryService.deleteInventoryForProduct(productId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
