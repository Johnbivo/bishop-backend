package com.bivolaris.billingservice.repositories;

import com.bivolaris.billingservice.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
    List<PaymentMethod> getAllByBillingAccountId(UUID billingAccountId);

    Optional<PaymentMethod> getPaymentMethodByBillingAccountId(UUID billingAccountId);
}
