package com.pushpey.ecommerce.controller;

import com.pushpey.ecommerce.entity.Order;
import com.pushpey.ecommerce.entity.User;
import com.pushpey.ecommerce.repository.UserRepository;
import com.pushpey.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .orElseThrow();
    }

    @PostMapping("/place")
    public Order placeOrder() {
        return orderService.placeOrder(getCurrentUser());
    }
}