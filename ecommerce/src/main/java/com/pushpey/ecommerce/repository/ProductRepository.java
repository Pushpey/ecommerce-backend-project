package com.pushpey.ecommerce.repository;

import com.pushpey.ecommerce.entity.Product;
import com.pushpey.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySeller(User seller);
}