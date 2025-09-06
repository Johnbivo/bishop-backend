package com.bivolaris.orderservice.mappers;

import com.bivolaris.orderservice.dtos.CartDto;
import com.bivolaris.orderservice.dtos.CreateCartRequestDto;
import com.bivolaris.orderservice.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toCartDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    Cart toCartEntity(CreateCartRequestDto createCartRequestDto);
}
