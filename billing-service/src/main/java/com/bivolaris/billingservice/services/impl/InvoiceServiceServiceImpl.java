package com.bivolaris.billingservice.services.impl;

import com.bivolaris.billingservice.dtos.InvoiceDto;
import com.bivolaris.billingservice.exceptions.InvoiceNotFoundException;
import com.bivolaris.billingservice.mappers.InvoiceMapper;
import com.bivolaris.billingservice.repositories.InvoiceRepository;
import com.bivolaris.billingservice.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvoiceServiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;


    @Override
    public List<InvoiceDto> getAllInvoices(){
        var invoices = invoiceRepository.findAll();
        if(invoices.isEmpty()){
            throw new InvoiceNotFoundException("There are no Invoices.");
        }
        return invoices.stream()
                .map(invoiceMapper::toInvoiceDto)
                .toList();
    }

    @Override
    public InvoiceDto getBillingAccountById(UUID id){
        var invoice = invoiceRepository.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Invoice not found."));
        return invoiceMapper.toInvoiceDto(invoice);
    }

    @Override
    public List<InvoiceDto> getBillingAccountByUserId(UUID accountId){
        var invoices = invoiceRepository.getInvoiceByBillingAccountId(accountId);
        if(invoices.isEmpty()){
            throw new InvoiceNotFoundException("There are no Invoices with this account id.");
        }
        return invoices.stream().map(invoiceMapper::toInvoiceDto).toList();
    }


}
