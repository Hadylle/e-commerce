package com.bezkoder.spring.jwt.mongodb;

import com.bezkoder.spring.jwt.mongodb.models.Category;
import com.bezkoder.spring.jwt.mongodb.repository.categoryRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CategoryDeserializer extends JsonDeserializer<Category> {

    @Autowired
    private categoryRepository categoryRepo;

    @Override
    public Category deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String name = jsonParser.getText();
        return categoryRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
