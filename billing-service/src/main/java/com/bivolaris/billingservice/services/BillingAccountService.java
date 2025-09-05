package com.bivolaris.billingservice.services;

import com.bivolaris.billingservice.dtos.BillingAccountDto;
import com.bivolaris.billingservice.dtos.BillingAccountGrpsResponse;
import com.bivolaris.billingservice.dtos.CreateBillingAccountGrpcRequestDto;

import java.util.List;
import java.util.UUID;

public interface BillingAccountService {

    public List<BillingAccountDto> getAllBillingAccounts();
    public BillingAccountDto getBillingAccountById(UUID id);
    public BillingAccountDto getBillingAccountByUserId(UUID userId);
    public BillingAccountGrpsResponse createBillingAccountService(CreateBillingAccountGrpcRequestDto createBillingAccountDto);
    public boolean deleteBillingAccount(UUID id);
}
