package com.bivolaris.billingservice.mappers;

import com.bivolaris.billingservice.dtos.InvoiceDto;
import com.bivolaris.billingservice.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceDto toInvoiceDto(Invoice invoice);
}
