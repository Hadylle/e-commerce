package com.bezkoder.spring.jwt.mongodb.services;

import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Integer product_id);
    List<Product> getProductsByCategory(Integer name);
    Product updateProduct(Integer product_id, Product updatedProduct);
    void deleteProduct(Integer product_id);

    // Filtering methods
    List<Product> findByNameContaining(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByDescriptionContaining(String description);
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByPriceLessThan(Double price);
    List<Product> findByPriceBetween(Double from, Double to);
    List<Product> findByQuantityGreaterThan(int quantity);
    List<Product> findByCategory(String category);

    List<Product> filterProducts(String name, String description, Double minPrice, Double maxPrice, Integer categoryId);

}
