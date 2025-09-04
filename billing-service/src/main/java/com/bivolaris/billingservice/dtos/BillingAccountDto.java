package com.bivolaris.billingservice.dtos;


import com.bivolaris.billingservice.entities.BillingAccountStatusEnum;
import com.bivolaris.billingservice.entities.CurrencyEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BillingAccountDto {

    private UUID id;
    private UUID userId;
    private CurrencyEnum currency;
    private BillingAccountStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
