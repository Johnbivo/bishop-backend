package com.bivolaris.orderservice.repositories;

import com.bivolaris.orderservice.entities.Cart;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findBySessionId(String sessionId);
    Optional<Cart> findByUserId(UUID userId);

    @Query("DELETE FROM Cart c WHERE c.expiresAt < :now")
    void deleteExpiredCarts(@Param("now") LocalDateTime now);
}
