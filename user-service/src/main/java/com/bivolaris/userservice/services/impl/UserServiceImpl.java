package com.bivolaris.userservice.services.impl;


import com.bivolaris.userservice.dtos.*;
import com.bivolaris.userservice.entities.CurrencyEnum;
import com.bivolaris.userservice.entities.User;
import com.bivolaris.userservice.exceptions.UserCreationException;
import com.bivolaris.userservice.exceptions.UsersNotFoundException;
import com.bivolaris.userservice.grpc.AccountBillingService;
import com.bivolaris.userservice.mappers.UserMapper;
import com.bivolaris.userservice.repositories.UserRepository;
import com.bivolaris.userservice.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountBillingService billingClient;

    private AccountBillingService accountBillingService;



    @Override
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

    @Override
    @Transactional
    public UserDetailsDto getUserById(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsersNotFoundException("User not found"));
        return userMapper.toUserDetailsDto(user);

    }

    @Override
    @Transactional
    public boolean createUser(CreateUserRequestDto createUserRequestDto){

        User user = userMapper.toUserEntity(createUserRequestDto);
        if (user == null) {
            throw new UserCreationException("User could not be created from the provided data");
        }


        userRepository.save(user);

        try {
            CreateBillingAccountRequest billingRequest = new CreateBillingAccountRequest();
            billingRequest.setUserId(user.getId());
            billingRequest.setCurrency(CurrencyEnum.USD);


            BillingAccountResponse billingResponse = billingClient.createBillingAccount(billingRequest);


            user.setBillingAccountId(billingResponse.getBillingAccountId().toString());
            user.setBillingAccountStatus(billingResponse.getBillingAccountStatus());
            user.setBillingCreatedAt(billingResponse.getCreatedAt());

            userRepository.save(user);
        } catch (Exception e) {
            throw new UserCreationException("User created, but failed to create billing account: " + e.getMessage());
        }

        return true;
    }


    @Override
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

    @Override
    @Transactional
    public boolean deleteUser(UUID id){
        var user = userRepository.findById(id).orElseThrow(() -> new UsersNotFoundException("User not found"));
        userRepository.delete(user);

        boolean billingDeleted = false;
        try {
            billingDeleted = accountBillingService.deleteBillingAccount(user.getBillingAccountId());
        } catch (Exception e) {
            // Optionally log the exception
            // The transaction for user deletion will still succeed
            e.printStackTrace();
        }

        return true;
    }








}
