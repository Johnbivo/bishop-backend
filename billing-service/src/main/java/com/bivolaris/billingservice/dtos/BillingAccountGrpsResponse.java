package com.bivolaris.billingservice.dtos;


import com.bivolaris.billingservice.entities.BillingAccountStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BillingAccountGrpsResponse {

    private UUID billingAccountId;
    private BillingAccountStatusEnum billingAccountStatus;
    private LocalDateTime createdAt;
}
