package com.bivolaris.productservice.services;

import com.bivolaris.productservice.dtos.CreateInventoryForProductDto;
import com.bivolaris.productservice.dtos.InventoryDto;
import com.bivolaris.productservice.dtos.UpdateInventoryRequestDto;

import java.util.List;

public interface InventoryService {

    public List<InventoryDto> getAllInventory();
    public InventoryDto getInventoryByProductId(String productId);
    public boolean createInventoryForProduct(CreateInventoryForProductDto createInventoryForProductDto);
    public boolean updateInventoryForProduct(UpdateInventoryRequestDto updateInventoryRequestDto, String productId);
    public boolean deleteInventoryForProduct(String productId);


    List<InventoryDto> getInventoriesByProductIds(List<String> productIds);
}
