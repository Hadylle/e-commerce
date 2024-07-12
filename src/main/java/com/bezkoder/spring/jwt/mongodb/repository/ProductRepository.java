package com.bezkoder.spring.jwt.mongodb.repository;


import com.bezkoder.spring.jwt.mongodb.models.Category;
import com.bezkoder.spring.jwt.mongodb.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
