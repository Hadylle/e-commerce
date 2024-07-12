package com.bezkoder.spring.jwt.mongodb.repository;


import com.bezkoder.spring.jwt.mongodb.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface categoryRepository extends MongoRepository<Category,Integer> {
    Optional<Category> findByName(String name);

}
