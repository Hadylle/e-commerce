package com.bezkoder.spring.jwt.mongodb.services;

import com.bezkoder.spring.jwt.mongodb.models.Category;
import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.repository.ProductRepository;
import com.bezkoder.spring.jwt.mongodb.repository.categoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final categoryRepository categoryRepo;

    public CategoryService(categoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }

    public Optional<Category> getCategoryById(Integer category_id){
        return categoryRepo.findById(category_id);
    }



    public Category updateCategory(Integer category_id, Category updatedCategory) {
        Optional<Category> existingCategory = categoryRepo.findById(category_id);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(updatedCategory.getName());

            return categoryRepo.save(category);
        } else {
            throw new RuntimeException("Product Not Found");
        }
    }
    public void deleteCategory (Integer category_id) {
        categoryRepo.deleteById(category_id);
    }
}
