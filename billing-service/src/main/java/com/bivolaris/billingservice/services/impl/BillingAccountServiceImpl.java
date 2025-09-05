package com.bivolaris.billingservice.services.impl;

import com.bivolaris.billingservice.dtos.BillingAccountDto;
import com.bivolaris.billingservice.dtos.BillingAccountGrpsResponse;
import com.bivolaris.billingservice.dtos.CreateBillingAccountGrpcRequestDto;
import com.bivolaris.billingservice.entities.BillingAccount;
import com.bivolaris.billingservice.entities.BillingAccountStatusEnum;
import com.bivolaris.billingservice.exceptions.BillingAccountAlreadyExistsException;
import com.bivolaris.billingservice.exceptions.BillingAccountNotFoundException;
import com.bivolaris.billingservice.mappers.BillingAccountMapper;
import com.bivolaris.billingservice.repositories.BillingAccountRepository;
import com.bivolaris.billingservice.services.BillingAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BillingAccountServiceImpl implements BillingAccountService {

    private final BillingAccountRepository billingAccountRepository;
    private final BillingAccountMapper billingAccountMapper;


    @Override
    public List<BillingAccountDto> getAllBillingAccounts(){
        var billingAccounts = billingAccountRepository.findAll();
        if(billingAccounts.isEmpty()){
            throw new BillingAccountNotFoundException("There are no billing accounts.");
        }
        return billingAccounts.stream()
                .map(billingAccountMapper::toBillingAccountDto)
                .toList();

    }


    @Override
    public BillingAccountDto getBillingAccountById(UUID id){
        var billingAccount = billingAccountRepository.findById(id).orElseThrow(() -> new BillingAccountNotFoundException("Billing account with id: " + id + " not found."));
        return billingAccountMapper.toBillingAccountDto(billingAccount);
    }

    @Override
    public BillingAccountDto getBillingAccountByUserId(UUID userId){
        var billingAccount = billingAccountRepository.findByUserId(userId).orElseThrow(() -> new BillingAccountNotFoundException("Billing account with id: " + userId + " not found."));
        return billingAccountMapper.toBillingAccountDto(billingAccount);
    }

    @Transactional
    @Override
    public BillingAccountGrpsResponse createBillingAccountService(CreateBillingAccountGrpcRequestDto request){
        var user =  billingAccountRepository.findByUserId(request.getUserId());
        if (user.isPresent()){
            throw new BillingAccountAlreadyExistsException("Billing Account Already Exists");
        }
        BillingAccount  billingAccount = new BillingAccount();
        billingAccount.setUserId(request.getUserId());
        billingAccount.setCurrency(request.getCurrency().name());
        billingAccount.setStatus(BillingAccountStatusEnum.ACTIVE);

        BillingAccount savedAccount = billingAccountRepository.save(billingAccount);

        return billingAccountMapper.toBillingAccountGrpcResponse(savedAccount);
    }


    @Override
    public boolean deleteBillingAccount(UUID id){
        var  billingAccount = billingAccountRepository.findById(id).orElseThrow(() -> new BillingAccountNotFoundException("Billing Account with id: " + id + " not found."));
        billingAccountRepository.delete(billingAccount);
        return true;
    }
}
