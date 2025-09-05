package com.bivolaris.orderservice.repositories;

import com.bivolaris.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}