package com.bezkoder.spring.jwt.mongodb.repository;


import com.bezkoder.spring.jwt.mongodb.models.Category;
import com.bezkoder.spring.jwt.mongodb.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByDescriptionContaining(String description);
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByPriceLessThan(Double price);
    List<Product> findByPriceBetween(Double from, Double to);
    List<Product> findByQuantityGreaterThan(int quantity);
    List<Product> findByCategoryName(String category);

    @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'description': { $regex: ?1, $options: 'i' }, 'price': { $gte: ?2, $lte: ?3 }, 'categoryId': ?4 }")
    List<Product> findProducts(String name, String description, Double minPrice, Double maxPrice, Integer categoryId);
}
