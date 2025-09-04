package com.bivolaris.userservice.dtos;


import com.bivolaris.userservice.entities.CurrencyEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBillingAccountRequest {

    private UUID userId;
    private CurrencyEnum currency;
}
