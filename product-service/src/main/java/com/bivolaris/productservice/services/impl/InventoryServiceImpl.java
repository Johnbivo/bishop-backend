package com.bivolaris.productservice.services.impl;

import com.bivolaris.productservice.dtos.CreateInventoryForProductDto;
import com.bivolaris.productservice.dtos.InventoryDto;
import com.bivolaris.productservice.dtos.UpdateInventoryRequestDto;
import com.bivolaris.productservice.exceptions.InventoryCreationException;
import com.bivolaris.productservice.exceptions.InventoryNotFoundException;
import com.bivolaris.productservice.mappers.InventoryMapper;
import com.bivolaris.productservice.repositories.InventoryRepository;
import com.bivolaris.productservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public List<InventoryDto> getAllInventory(){
        var inventory = inventoryRepository.findAll();
        if(inventory.isEmpty()){
            throw new InventoryNotFoundException("Inventory does not exist.");
        }
        return inventory.stream().map(inventoryMapper::toInventoryDto).toList();
    }

    @Override
    public InventoryDto getInventoryByProductId(String productId){
        var  inventory = inventoryRepository.findById(productId).orElseThrow(() -> new InventoryNotFoundException("Inventory does not exist."));
        return inventoryMapper.toInventoryDto(inventory);
    }

    @Override
    public boolean createInventoryForProduct(CreateInventoryForProductDto request){
        try{
            var inventory = inventoryMapper.toInventoryEntity(request);
            inventoryRepository.save(inventory);
        }catch(Exception e){
            throw new InventoryCreationException("Inventory could not be created: " + e.getMessage());
        }
        return true;

    }

    @Override
    public boolean updateInventoryForProduct(UpdateInventoryRequestDto request, String productId){
        try{
            var inventory = inventoryRepository.findById(productId).orElseThrow(() -> new InventoryNotFoundException("Inventory does not exist."));

            Optional.ofNullable(request.getAvailableQuantity()).ifPresent(inventory::setAvailableQuantity);
            Optional.ofNullable(request.getReservedQuantity()).ifPresent(inventory::setReservedQuantity);
            Optional.ofNullable(request.getReorderLevel()).ifPresent(inventory::setReorderLevel);

            inventoryRepository.save(inventory);
        }catch(Exception e){
            throw new InventoryCreationException("Inventory could not be updated: " + e.getMessage());
        }

        return true;

    }


    @Override
    public boolean deleteInventoryForProduct(String productId){
        var inventory =  inventoryRepository.findById(productId).orElseThrow(() -> new InventoryNotFoundException("Inventory does not exist."));
        inventoryRepository.delete(inventory);
        return true;
    }
}
