package com.bivolaris.userservice.services.impl;


import com.bivolaris.userservice.dtos.CreateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UpdateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UserAddressDto;
import com.bivolaris.userservice.entities.User;
import com.bivolaris.userservice.entities.UserAddress;
import com.bivolaris.userservice.exceptions.UserAddressCreationException;
import com.bivolaris.userservice.exceptions.UserAddressNotFoundException;
import com.bivolaris.userservice.exceptions.UsersNotFoundException;
import com.bivolaris.userservice.mappers.UserAddressMapper;
import com.bivolaris.userservice.repositories.UserAddressRepository;
import com.bivolaris.userservice.repositories.UserRepository;
import com.bivolaris.userservice.services.UserAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserAddressServiceImpl implements UserAddressService {


    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserAddressDto> getAllAddresses(){
        var addresses = userAddressRepository.findAll().stream()
                .map(userAddressMapper::toUserAddressDto)
                .toList();
        if(addresses.isEmpty()){
            throw new UserAddressNotFoundException("User addresses not found");
        }

        return addresses;
    }

    @Override
    public List<UserAddressDto> getUserAddresses(UUID id){
        var addresses = userAddressRepository.findByUserId(id).stream()
                .map(userAddressMapper::toUserAddressDto)
                .toList();

        if(addresses.isEmpty()){
            throw new UserAddressNotFoundException("User addresses not found");
        }
        return addresses;
    }

    @Override
    @Transactional
    public UserAddressDto getUserAddress(UUID id){
        var address = userAddressRepository.findById(id).orElseThrow( () -> new UserAddressNotFoundException("Address not found"));
        return userAddressMapper.toUserAddressDto(address);
    }

    @Override
    @Transactional
    public boolean createUserAddress(CreateUserAddressRequestDto request){
        UserAddress userAddress = userAddressMapper.toUserAddress(request);
        if(userAddress == null){
            throw new UserAddressCreationException("Cannot create user address");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UsersNotFoundException("User not found"));

        userAddress.setUser(user);
        userAddressRepository.save(userAddress);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUserAddress(UpdateUserAddressRequestDto request, UUID id){

        var userAddress = userAddressRepository.findById(id).orElseThrow( () -> new UserAddressNotFoundException("Address not found"));

        Optional.ofNullable(request.getAddressLine1()).ifPresent(userAddress::setAddressLine1);
        Optional.ofNullable(request.getAddressLine2()).ifPresent(userAddress::setAddressLine2);
        Optional.ofNullable(request.getCity()).ifPresent(userAddress::setCity);
        Optional.ofNullable(request.getState()).ifPresent(userAddress::setState);
        Optional.ofNullable(request.getPostalCode()).ifPresent(userAddress::setPostalCode);
        Optional.ofNullable(request.getCountry()).ifPresent(userAddress::setCountry);
        Optional.ofNullable(request.getAddressType()).ifPresent(userAddress:: setType);
        Optional.ofNullable(request.getIsDefault()).ifPresent(userAddress::setIsDefault);

        userAddressRepository.save(userAddress);
        return true;

    }

    @Override
    @Transactional
    public boolean deleteUserAddress(UUID id){
        var userAddress = userAddressRepository.findById(id).orElseThrow( () -> new UserAddressNotFoundException("Address not found"));
        userAddressRepository.delete(userAddress);
        return true;
    }
}
