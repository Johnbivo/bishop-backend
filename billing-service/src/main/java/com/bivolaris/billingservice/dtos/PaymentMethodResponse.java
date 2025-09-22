package com.bivolaris.billingservice.dtos;


import com.bivolaris.billingservice.entities.PaymentProviderEnum;
import com.bivolaris.billingservice.entities.PaymentTypeEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentMethodResponse {

    private UUID id;
}
