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
public class ProductServiceImpl implements  ProductService{

    private final ProductRepository productRepo;
    private final categoryRepository categoryRepo;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, categoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> getProductById(Integer product_id){
        return productRepo.findById(product_id);
    }

    @Override
    public List<Product> getProductsByCategory(Integer category_id) {
        Category category = categoryRepo.findById(category_id).orElse(null);
        if (category != null) {
            return productRepo.findByCategory(category);
        } else {
            return null;
        }
    }

    @Override
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
            throw new RuntimeException("Product Not Found");
        }
    }

    @Override
    public void deleteProduct(Integer product_id) {
        productRepo.deleteById(product_id);
    }

    // Filtering methods
    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepo.findByNameContaining(name);
    }

    @Override
    public List<Product> findByNameStartingWith(String name) {
        return productRepo.findByNameStartingWith(name);
    }

    @Override
    public List<Product> findByNameEndingWith(String name) {
        return productRepo.findByNameEndingWith(name);
    }

    @Override
    public List<Product> findByDescriptionContaining(String description) {
        return productRepo.findByDescriptionContaining(description);
    }

    @Override
    public List<Product> findByPriceGreaterThan(Double price) {
        return productRepo.findByPriceGreaterThan(price);
    }

    @Override
    public List<Product> findByPriceLessThan(Double price) {
        return productRepo.findByPriceLessThan(price);
    }

    @Override
    public List<Product> findByPriceBetween(Double from, Double to) {
        return productRepo.findByPriceBetween(from, to);
    }

    @Override
    public List<Product> findByQuantityGreaterThan(int quantity) {
        return productRepo.findByQuantityGreaterThan(quantity);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> filterProducts(String name, String description, Double minPrice, Double maxPrice, Integer categoryId) {
        return productRepo.findProducts(name, description, minPrice, maxPrice, categoryId);
    }
}