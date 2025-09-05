package com.bivolaris.productservice.mappers;


import com.bivolaris.productservice.dtos.CreateInventoryForProductDto;
import com.bivolaris.productservice.dtos.InventoryDto;
import com.bivolaris.productservice.entities.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryDto toInventoryDto(Inventory inventory);

    Inventory toInventoryEntity(CreateInventoryForProductDto createInventoryForProductDto);
}
