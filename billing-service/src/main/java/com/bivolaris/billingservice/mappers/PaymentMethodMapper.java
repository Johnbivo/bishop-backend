package com.bivolaris.billingservice.mappers;


import com.bivolaris.billingservice.dtos.PaymentMethodDto;
import com.bivolaris.billingservice.dtos.PaymentMethodResponse;
import com.bivolaris.billingservice.entities.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {


    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
    PaymentMethodDto toPaymentMethodDto(PaymentMethod paymentMethod);
}
