package com.bivolaris.userservice.services;

import com.bivolaris.userservice.dtos.CreateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UpdateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UserAddressDto;

import java.util.List;
import java.util.UUID;

public interface UserAddressService {

    public List<UserAddressDto> getAllAddresses();
    public List<UserAddressDto> getUserAddresses(UUID id);
    public UserAddressDto getUserAddress(UUID id);
    public boolean createUserAddress(CreateUserAddressRequestDto request);
    public boolean updateUserAddress(UpdateUserAddressRequestDto request, UUID id);
    public boolean deleteUserAddress(UUID id);
}
