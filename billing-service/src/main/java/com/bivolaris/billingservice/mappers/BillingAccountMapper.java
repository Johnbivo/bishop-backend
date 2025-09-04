package com.bivolaris.billingservice.mappers;

import com.bivolaris.billingservice.dtos.BillingAccountDto;
import com.bivolaris.billingservice.dtos.BillingAccountGrpsResponse;
import com.bivolaris.billingservice.entities.BillingAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillingAccountMapper {

    BillingAccountDto toBillingAccountDto(BillingAccount billingAccount);

    @Mapping(target = "billingAccountId", source = "id")
    @Mapping(target = "billingAccountStatus", source = "status")
    BillingAccountGrpsResponse toBillingAccountGrpcResponse(BillingAccount billingAccount);
}
