package com.pushpey.ecommerce.repository;

import com.pushpey.ecommerce.entity.OrderItem;
import com.pushpey.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByProduct_Seller(User seller);
}