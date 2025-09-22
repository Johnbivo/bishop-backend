package com.bivolaris.billingservice.services.impl;

import com.bivolaris.billingservice.dtos.PaymentMethodDto;
import com.bivolaris.billingservice.dtos.PaymentMethodResponse;
import com.bivolaris.billingservice.mappers.PaymentMethodMapper;
import com.bivolaris.billingservice.repositories.PaymentMethodRepository;
import com.bivolaris.billingservice.services.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {


    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;


    @Override
    public List<PaymentMethodDto> getAllPaymentMethodsByBillingAccountId(UUID billingAccountId){
        try {
            var paymentMethods = paymentMethodRepository.getAllByBillingAccountId(billingAccountId);
            return paymentMethods.stream().map(paymentMethodMapper::toPaymentMethodDto).toList();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return  null;
    }

    @Override
    public PaymentMethodDto getPaymentMethodById(UUID billingAccountId){
        try {
            var paymentMethod = paymentMethodRepository.getPaymentMethodByBillingAccountId(billingAccountId).orElse(null);
            if(paymentMethod != null){
                return paymentMethodMapper.toPaymentMethodDto(paymentMethod);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;

    }




    @Override
    public PaymentMethodResponse getPaymentOrder(UUID billingAccountId){


    }
}
