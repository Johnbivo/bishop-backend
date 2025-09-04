package com.bivolaris.billingservice.repositories;

import com.bivolaris.billingservice.entities.BillingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BillingAccountRepository extends JpaRepository<BillingAccount, UUID> {

    Optional<BillingAccount> findByUserId(UUID userId);
}
