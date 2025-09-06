package com.bivolaris.productservice.mappers;


import com.bivolaris.productservice.dtos.CreateInventoryForProductDto;
import com.bivolaris.productservice.dtos.InventoryDto;
import com.bivolaris.productservice.entities.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryDto toInventoryDto(Inventory inventory);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalQuantity", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastRestocked", ignore = true)
    Inventory toInventoryEntity(CreateInventoryForProductDto createInventoryForProductDto);
}
