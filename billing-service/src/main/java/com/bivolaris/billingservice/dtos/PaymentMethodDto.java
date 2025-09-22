package com.bivolaris.billingservice.dtos;


import com.bivolaris.billingservice.entities.PaymentProviderEnum;
import com.bivolaris.billingservice.entities.PaymentTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentMethodDto {

    private UUID id;
    private UUID billingAccountId;
    private PaymentTypeEnum type;
    private PaymentProviderEnum provider;
    private String token;
    private String lastFour;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime expiresAt;

    private Boolean isDefault;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime createdAt;


}