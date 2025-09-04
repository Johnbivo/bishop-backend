package com.bivolaris.billingservice.dtos;

import com.bivolaris.billingservice.entities.CurrencyEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBillingAccountGrpcRequestDto {

    private UUID userId;
    private CurrencyEnum currency;
}
