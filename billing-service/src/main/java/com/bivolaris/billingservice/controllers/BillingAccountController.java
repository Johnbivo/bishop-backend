package com.bivolaris.billingservice.controllers;


import com.bivolaris.billingservice.dtos.BillingAccountDto;
import com.bivolaris.billingservice.services.BillingAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/billing-accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;


    @Operation(method = "getAllBillingAccounts()", description = "Returns all billing accounts.")
    @GetMapping
    public ResponseEntity<List<BillingAccountDto>> getAllBillingAccounts(){
        return ResponseEntity.status(HttpStatus.OK).body(billingAccountService.getAllBillingAccounts());
    }


    @Operation(method = "getBillingAccountById()", description = "Returns the billing account with the provided UUID.")
    @GetMapping("/{id}")
    public ResponseEntity<BillingAccountDto> getBillingAccountById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(billingAccountService.getBillingAccountById(id));
    }

    @Operation(method = "getBillingAccountByUserId()", description = "Returns the billing account with the provided userId UUID.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<BillingAccountDto> getBillingAccountByUserId(@PathVariable UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(billingAccountService.getBillingAccountByUserId(userId));
    }
}
