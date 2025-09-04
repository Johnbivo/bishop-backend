package com.bivolaris.billingservice.services;

import com.bivolaris.billingservice.dtos.InvoiceDto;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    public List<InvoiceDto> getAllInvoices();
    public InvoiceDto getBillingAccountById(UUID id);
    public List<InvoiceDto> getBillingAccountByUserId(UUID id);
}
