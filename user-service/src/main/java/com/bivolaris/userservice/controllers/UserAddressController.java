package com.bivolaris.userservice.controllers;


import com.bivolaris.userservice.dtos.CreateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UpdateUserAddressRequestDto;
import com.bivolaris.userservice.dtos.UserAddressDto;
import com.bivolaris.userservice.services.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user-addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;


    @Operation(method = "getAddresses", description = "Get all addresses and the name and id of the user it belongs to.")
    @GetMapping
    public ResponseEntity<List<UserAddressDto>> getAddresses(){
        return ResponseEntity.ok(userAddressService.getAllAddresses());
    }

    @Operation(method = "getUserAddresses", description = "Get all the addresses of a user based on uuid.")
    @GetMapping("/{id}")
    public ResponseEntity<List<UserAddressDto>> getUserAddresses(@PathVariable UUID id){
        return ResponseEntity.ok(userAddressService.getUserAddresses(id));

    }

    @Operation(method = "getUserAddress", description = "Get a single address based on uuid.")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserAddressDto> getUserAddress(@PathVariable UUID id){
        return ResponseEntity.ok(userAddressService.getUserAddress(id));
    }


    @Operation(method = "createUserAddress", description = "Create a new user address.")
    @PostMapping
    public ResponseEntity<Void> createUserAddress(CreateUserAddressRequestDto request){
        if(!userAddressService.createUserAddress(request)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @Operation(method = "updateUserAddress", description = "Update the user address.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserAddress(@RequestBody UpdateUserAddressRequestDto request, @PathVariable UUID id){
        if(!userAddressService.updateUserAddress(request,id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();

    }

    @Operation(method = "deleteUserAddress", description = "Delete the user address.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable UUID id){
        userAddressService.deleteUserAddress(id);
        return ResponseEntity.ok().build();
    }





}
