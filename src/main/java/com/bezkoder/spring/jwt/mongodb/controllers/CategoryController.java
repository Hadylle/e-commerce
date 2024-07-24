package com.bezkoder.spring.jwt.mongodb.controllers;

import com.bezkoder.spring.jwt.mongodb.models.Category;
import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/add")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category newCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping("/list")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/id/{category_id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer category_id) {
        Optional<Category> category = categoryService.getCategoryById(category_id);
        return category.map(ResponseEntity ::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Optional<Category> category = categoryService.getCategoryByName(name);
        return category.map(ResponseEntity ::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer category_id, @RequestBody Category category){
        Category updateCategory = categoryService.updateCategory(category_id,category);
        return ResponseEntity.ok(updateCategory);
    }
    @DeleteMapping("/list/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer category_id) {
        categoryService.deleteCategory(category_id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
