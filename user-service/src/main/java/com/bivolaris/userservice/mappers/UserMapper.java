package com.bivolaris.userservice.mappers;


import com.bivolaris.userservice.dtos.CreateUserRequestDto;
import com.bivolaris.userservice.dtos.UserDetailsDto;
import com.bivolaris.userservice.dtos.UserSummaryDto;
import com.bivolaris.userservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserSummaryDto toUserSummaryDto(User user);
    UserDetailsDto toUserDetailsDto(User user);


    User toUserEntity(CreateUserRequestDto createUserRequestDto);
}
