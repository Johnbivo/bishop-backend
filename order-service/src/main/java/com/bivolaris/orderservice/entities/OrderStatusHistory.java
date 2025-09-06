package com.bivolaris.orderservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum oldStatus;

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum newStatus;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @Column(name = "notes")
    private String notes;




}
