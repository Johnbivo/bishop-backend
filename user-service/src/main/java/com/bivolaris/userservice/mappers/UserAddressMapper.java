package com.bivolaris.userservice.mappers;



import com.bivolaris.userservice.dtos.CreateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UserAddressDto;
import com.bivolaris.userservice.entities.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "userLastName", source = "user.lastName")
    @Mapping(target = "addressType", source = "type")
    UserAddressDto toUserAddressDto(UserAddress userAddress);

    @Mapping(target = "type", source = "addressType")
    UserAddress toUserAddress(CreateUserAddressRequestDto request);



}
