package com.bivolaris.billingservice.controllers;


import com.bivolaris.billingservice.dtos.InvoiceDto;
import com.bivolaris.billingservice.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/billing-accounts/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;


    @Operation(method = "getAllInvoices()", description = "Returns all invoices.")
    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok().body(invoiceService.getAllInvoices());
    }

    @Operation(method = "getInvoiceByAccountId()", description = "Returns the invoice with the provided id.")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceByAccountId(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getBillingAccountById(id));
    }

    @Operation(method = "getInvoiceByAccountUserId()", description = "Returns the invoice with the provided user id.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceDto>> getInvoiceByAccountUserId(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getBillingAccountByUserId(userId));
    }
}
