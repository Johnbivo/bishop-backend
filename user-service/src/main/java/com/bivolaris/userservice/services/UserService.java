package com.bivolaris.userservice.services;


import com.bivolaris.userservice.dtos.CreateUserRequestDto;
import com.bivolaris.userservice.dtos.UserDetailsDto;
import com.bivolaris.userservice.dtos.UserSummaryDto;
import com.bivolaris.userservice.entities.User;
import com.bivolaris.userservice.exceptions.UserCreationException;
import com.bivolaris.userservice.exceptions.UsersNotFoundException;
import com.bivolaris.userservice.mappers.UserMapper;
import com.bivolaris.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;



    public List<UserSummaryDto> getAllUsers(){
        List<UserSummaryDto> users = userRepository.findAll()
                .stream()
                .map(userMapper::toUserSummaryDto)
                .toList();

        if (users.isEmpty()) {
            throw new UsersNotFoundException("No users found");
        }
        return users;
    }

    @Transactional
    public UserDetailsDto getUserById(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsersNotFoundException("User not found"));
        return userMapper.toUserDetailsDto(user);

    }

    @Transactional
    public boolean createUser(CreateUserRequestDto createUserRequestDto){
        var user = userMapper.toUserEntity(createUserRequestDto);
        if (user == null) {
            throw new UserCreationException("User could not be created from the provided data");
        }


        userRepository.save(user);
        return true;

    }


    @Transactional
    public boolean updateUser(UserDetailsDto userDetailsDto, UUID id){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsersNotFoundException("User not found");
        }

        Optional.ofNullable(userDetailsDto.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userDetailsDto.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userDetailsDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userDetailsDto.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(userDetailsDto.getGender()).ifPresent(user::setGender);
        Optional.ofNullable(userDetailsDto.getDateOfBirth()).ifPresent(user::setDateOfBirth);
        Optional.ofNullable(userDetailsDto.getBillingAccountStatus()).ifPresent(user::setBillingAccountStatus);
        Optional.ofNullable(userDetailsDto.getMarketingEmails()).ifPresent(user::setMarketingEmails);
        Optional.ofNullable(userDetailsDto.getOrderNotifications()).ifPresent(user::setOrderNotifications);

        userRepository.save(user);
        return true;

    }

    @Transactional
    public boolean deleteUser(UUID id){
        var user = userRepository.findById(id).orElseThrow(() -> new UsersNotFoundException("User not found"));
        userRepository.delete(user);
        return true;
    }






}
