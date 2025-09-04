package com.bivolaris.userservice.dtos;

import com.bivolaris.userservice.entities.BillingAccountStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BillingAccountResponse {

    private UUID billingAccountId;
    private BillingAccountStatusEnum billingAccountStatus;
    private LocalDateTime createdAt;
}
