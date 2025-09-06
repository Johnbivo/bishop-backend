package com.bivolaris.orderservice.repositories;

import com.bivolaris.orderservice.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<CartItem, UUID> {
}
