package com.bivolaris.billingservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "billing_account_id")
    private UUID billingAccountId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum type =  PaymentTypeEnum.CARD;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private PaymentProviderEnum provider = PaymentProviderEnum.STRIPE;

    @Column(name = "token")
    private String token;

    @Column(name = "last_four")
    private String lastFour;

    @Column(name = "brand")
    private String brand;

    @Column(name = "expires_at")
    private LocalDate expiresAt;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
