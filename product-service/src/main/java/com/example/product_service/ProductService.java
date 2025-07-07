package com.example.product_service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService {

    private static final Map<String, Product> PRODUCT_DB = Map.of(
            "p101", new Product("p101", "Laptop", 90000),
            "p102", new Product("p102", "Keyboard", 1500)
    );

    @Cacheable(value = "products", key = "#productId")
    public Product getProductById(String productId) {
        simulateSlowService();
        return PRODUCT_DB.get(productId);
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000); // Simulate slow DB call
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @CacheEvict(value = "products", key = "#productId")
    public void evictProductCache(String productId) {
        System.out.println("Product cache evicted for: " + productId);
    }

}

