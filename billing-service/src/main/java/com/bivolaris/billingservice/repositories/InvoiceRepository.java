package com.bivolaris.billingservice.repositories;

import com.bivolaris.billingservice.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> getInvoiceByBillingAccountId(UUID accountId);
}
