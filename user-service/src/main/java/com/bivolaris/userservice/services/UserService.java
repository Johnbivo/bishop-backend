package com.bivolaris.userservice.services;

import com.bivolaris.userservice.dtos.CreateUserRequestDto;
import com.bivolaris.userservice.dtos.UserDetailsDto;
import com.bivolaris.userservice.dtos.UserSummaryDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public List<UserSummaryDto> getAllUsers();
    public UserDetailsDto getUserById(UUID id);
    public boolean createUser(CreateUserRequestDto createUserRequestDto);
    public boolean updateUser(UserDetailsDto userDetailsDto, UUID id);
    public boolean deleteUser(UUID id);
}
