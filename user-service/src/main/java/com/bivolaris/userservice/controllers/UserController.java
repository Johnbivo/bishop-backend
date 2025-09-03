package com.bivolaris.userservice.controllers;


import com.bivolaris.userservice.dtos.CreateUserRequestDto;
import com.bivolaris.userservice.dtos.UserDetailsDto;
import com.bivolaris.userservice.dtos.UserSummaryDto;
import com.bivolaris.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(method= "getAllUsers()", description = "Returns all users in the system.")
    @GetMapping
    public ResponseEntity<List<UserSummaryDto>> getAllUsers(){
        return  ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(method = "getUserById()", description = "Returns the user with all details.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById( @Parameter(description = "UUID of the user to retrieve binary(16)", required = true)
                                                           @PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(method = "createUser(CreateUserRequestDto)", description = "Create a new user.")
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto){
       if(!userService.createUser(createUserRequestDto)){
           return ResponseEntity.badRequest().build();
       }
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(method = "updateUser", description = "Update the user with the given uuid and the updateRequestDto.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDetailsDto userDetailsDto,
                                           @PathVariable UUID id){
        if(!userService.updateUser(userDetailsDto, id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(method = "deleteUser", description = "Delete a user by the given uuid.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        if(!userService.deleteUser(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }












}
