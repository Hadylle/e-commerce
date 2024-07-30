package com.bezkoder.spring.jwt.mongodb.services;

import com.bezkoder.spring.jwt.mongodb.models.Category;
import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.repository.ProductRepository;
import com.bezkoder.spring.jwt.mongodb.repository.categoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final categoryRepository categoryRepo;

    @Autowired
    public ProductService(ProductRepository productRepo, categoryRepository categoryRepo, MongoTemplate mongoTemplate) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Optional<Product> getProductById(Integer product_id){
        return productRepo.findById(product_id);
    }

    public List<Product> getProductsByCategory(String name) {
        Category category = categoryRepo.findByName(name).orElse(null);
        if (category != null) {
            return productRepo.findByCategory(category);
        } else {
            return null;
        }
    }

    public Product updateProduct(Integer product_id, Product updatedProduct){
        Optional<Product> existingProduct = productRepo.findById(product_id);
        if(existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setImageURL(updatedProduct.getImageURL());
            return productRepo.save(product);
        }
        else {
            throw  new RuntimeException("Product Not Found");
        }
    }
    public void deleteProduct (Integer product_id) {
        productRepo.deleteById(product_id);
    }


    public List<Product> findByNameContaining(String name) {
        return productRepo.findByNameContaining(name);
    }

    public List<Product> findByNameStartingWith(String name) {
        return productRepo.findByNameStartingWith(name);
    }

    public List<Product> findByNameEndingWith(String name) {
        return productRepo.findByNameEndingWith(name);
    }

    public List<Product> findByDescriptionContaining(String description) {
        return productRepo.findByDescriptionContaining(description);
    }

    public List<Product> findByPriceGreaterThan(Double price) {
        return productRepo.findByPriceGreaterThan(price);
    }

    public List<Product> findByPriceLessThan(Double price) {
        return productRepo.findByPriceLessThan(price);
    }

    public List<Product> findByPriceBetween(Double from, Double to) {
        return productRepo.findByPriceBetween(from, to);
    }

    public List<Product> findByQuantityGreaterThan(int quantity) {
        return productRepo.findByQuantityGreaterThan(quantity);
    }


    public List<Product> findByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }
}

