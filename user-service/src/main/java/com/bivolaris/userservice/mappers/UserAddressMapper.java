package com.bivolaris.userservice.mappers;



import com.bivolaris.userservice.dtos.CreateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UserAddressDto;
import com.bivolaris.userservice.entities.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "userLastName", source = "user.lastName")
    @Mapping(target = "addressType", source = "type")
    UserAddressDto toUserAddressDto(UserAddress userAddress);

    @Mapping(target = "type", source = "addressType")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserAddress toUserAddress(CreateUserAddressRequestDto request);



}
