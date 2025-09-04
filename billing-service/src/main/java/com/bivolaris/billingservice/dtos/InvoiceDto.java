package com.bivolaris.billingservice.dtos;

import com.bivolaris.billingservice.entities.CurrencyEnum;
import com.bivolaris.billingservice.entities.InvoiceStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InvoiceDto {

    private UUID id;
    private UUID billingAccountId;
    private UUID orderId;
    private BigDecimal amount;
    private BigDecimal taxAmount;
    private CurrencyEnum currency;
    private InvoiceStatusEnum status;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;


}
