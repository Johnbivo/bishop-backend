package com.bivolaris.userservice.mappers;

import com.bivolaris.userservice.dtos.CreateUserRequestDto;
import com.bivolaris.userservice.dtos.UserDetailsDto;
import com.bivolaris.userservice.dtos.UserSummaryDto;
import com.bivolaris.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    UserSummaryDto toUserSummaryDto(User user);

    UserDetailsDto toUserDetailsDto(User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "billingAccountId", ignore = true)
    @Mapping(target = "billingAccountStatus", ignore = true)
    @Mapping(target = "billingCreatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUserEntity(CreateUserRequestDto createUserRequestDto);
}
