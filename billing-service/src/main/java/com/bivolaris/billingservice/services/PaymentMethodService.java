package com.bivolaris.billingservice.services;

import com.bivolaris.billingservice.dtos.ChangeDefaultPaymentMethodDto;
import com.bivolaris.billingservice.dtos.PaymentMethodDto;
import com.bivolaris.billingservice.dtos.PaymentMethodResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService {

    List<PaymentMethodDto> getAllPaymentMethodsByBillingAccountId(UUID billingAccountId);
    PaymentMethodDto getPaymentMethodById(UUID billingAccountId);
    PaymentMethodResponse getPaymentOrder(UUID billingAccountId);
}
