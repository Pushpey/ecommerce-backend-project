package com.pushpey.ecommerce.repository;

import com.pushpey.ecommerce.entity.CartItem;
import com.pushpey.ecommerce.entity.Cart;
import com.pushpey.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}