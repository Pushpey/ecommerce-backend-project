package com.pushpey.ecommerce.controller;

import com.pushpey.ecommerce.entity.Cart;
import com.pushpey.ecommerce.entity.User;
import com.pushpey.ecommerce.repository.UserRepository;
import com.pushpey.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // 🔥 Get logged-in user from JWT
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ USER ONLY
    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long productId,
                          @RequestParam int quantity) {

        User user = getCurrentUser();
        return cartService.addToCart(productId, quantity, user);
    }

    // ✅ USER ONLY
    @GetMapping
    public Cart getCart() {
        User user = getCurrentUser();
        return cartService.getOrCreateCart(user);
    }
}