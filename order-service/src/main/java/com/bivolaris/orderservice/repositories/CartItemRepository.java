package com.bivolaris.orderservice.repositories;

import com.bivolaris.orderservice.entities.Cart;
import com.bivolaris.orderservice.entities.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, UUID> {

    Optional<CartItem> findByCartAndProductId(Cart cart, UUID productId);

    @Transactional
    void deleteByCart(Cart cart);


    List<CartItem> findAllByCart(Cart cart);

    Optional<CartItem> findByCartIdAndProductId(UUID cartId, UUID productId);
}
