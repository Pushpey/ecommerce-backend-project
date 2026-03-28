package com.pushpey.ecommerce.service;

import com.pushpey.ecommerce.entity.Product;
import com.pushpey.ecommerce.entity.User;
import com.pushpey.ecommerce.repository.ProductRepository;
import com.pushpey.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // COMMON METHOD (reuse everywhere)
    private User getCurrentSeller() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Product addProduct(Product product) {
        User seller = getCurrentSeller();
        product.setSeller(seller);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getSellerProducts() {
        User seller = getCurrentSeller();
        return productRepository.findBySeller(seller);
    }

    public Product updateProduct(Long id, Product updated) {

        User seller = getCurrentSeller();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        product.setName(updated.getName());
        product.setPrice(updated.getPrice());
        product.setStock(updated.getStock());
        product.setDescription(updated.getDescription());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        User seller = getCurrentSeller();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        productRepository.delete(product);
    }
}