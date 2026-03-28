package com.pushpey.ecommerce.service;

import com.pushpey.ecommerce.entity.*;
import com.pushpey.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order placeOrder(User user) {

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem item : cart.getItems()) {

            Product product = item.getProduct();

            // 💀 stock check
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Out of stock: " + product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .build();

            total += product.getPrice() * item.getQuantity();
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .user(user)
                .items(orderItems)
                .totalPrice(total)
                .status("PLACED")
                .build();

        for (OrderItem oi : orderItems) {
            oi.setOrder(order);
        }

        cart.getItems().clear(); // 🧹 clear cart after order
        cartRepository.save(cart);

        return orderRepository.save(order);
    }
}