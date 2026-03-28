package com.pushpey.ecommerce.controller;

import com.pushpey.ecommerce.entity.Product;
import com.pushpey.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ✅ SELLER ONLY - Add Product
    @PostMapping("/seller/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // ✅ ALL (USER, SELLER, ADMIN) - Get All Products
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ SELLER ONLY - Get Own Products
    @GetMapping("/seller/my")
    public List<Product> getSellerProducts() {
        return productService.getSellerProducts();
    }

    // ✅ SELLER ONLY - Update Product
    @PutMapping("/seller/update/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // ✅ SELLER ONLY - Delete Product
    @DeleteMapping("/seller/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}