package com.bezkoder.spring.jwt.mongodb.controllers;

import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer product_id) {
        Optional<Product> product = productService.getProductById(product_id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


// do not get lost baby girl this is the endpoint you are searching for <3
    @GetMapping("products/category/{category_id}")
    public List<Product> getProductsByCategory(@PathVariable Integer category_id) {
        return productService.getProductsByCategory(category_id);
    }

    @PutMapping("/product/{product_id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer product_id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product_id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProduct(product_id);
        return ResponseEntity.ok("Product deleted successfully");
    }


   /* @GetMapping("/products/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nameStarting,
            @RequestParam(required = false) String nameEnding,
            @RequestParam(required = false) String description) {

        if (name != null) {
            return productService.findByNameContaining(name);
        } else if (nameStarting != null) {
            return productService.findByNameStartingWith(nameStarting);
        } else if (nameEnding != null) {
            return productService.findByNameEndingWith(nameEnding);
        } else if (description != null) {
            return productService.findByDescriptionContaining(description);
        } else {
            return Collections.emptyList(); // Return empty list if no valid query parameter is provided
        }
    } */

    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam(required = false) String data) {
        if (data == null || data.isEmpty()) {
            return Collections.emptyList(); // Return empty list if no data is provided
        }

        // Fetch results from different searches
        List<Product> byNameContaining = productService.findByNameContaining(data);
        List<Product> byNameStartingWith = productService.findByNameStartingWith(data);
        List<Product> byNameEndingWith = productService.findByNameEndingWith(data);
        List<Product> byDescriptionContaining = productService.findByDescriptionContaining(data);

        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Results by name containing '{}': {}", data, byNameContaining);
        logger.info("Results by name starting with '{}': {}", data, byNameStartingWith);
        logger.info("Results by name ending with '{}': {}", data, byNameEndingWith);
        logger.info("Results by description containing '{}': {}", data, byDescriptionContaining);


        // Combine results and remove duplicates
        Set<Product> combinedResults = new HashSet<>();
        combinedResults.addAll(byNameContaining);
        combinedResults.addAll(byNameStartingWith);
        combinedResults.addAll(byNameEndingWith);
        combinedResults.addAll(byDescriptionContaining);

        return new ArrayList<>(combinedResults); // Convert the set back to a list
    }

    @GetMapping("/products/search/name")
    public List<Product> findByNameContaining(@RequestParam String name) {
        return productService.findByNameContaining(name);
    }

    @GetMapping("/products/search/name/starting")
    public List<Product> findByNameStartingWith(@RequestParam String name) {
        return productService.findByNameStartingWith(name);
    }

    @GetMapping("/products/search/name/ending")
    public List<Product> findByNameEndingWith(@RequestParam String name) {
        return productService.findByNameEndingWith(name);
    }

    @GetMapping("/products/search/description")
    public List<Product> findByDescriptionContaining(@RequestParam String description) {
        return productService.findByDescriptionContaining(description);
    }

    @GetMapping("/products/search/price/greater")
    public List<Product> findByPriceGreaterThan(@RequestParam Double price) {
        return productService.findByPriceGreaterThan(price);
    }

    @GetMapping("/products/search/price/less")
    public List<Product> findByPriceLessThan(@RequestParam Double price) {
        return productService.findByPriceLessThan(price);
    }

    @GetMapping("/products/search/price/between")
    public List<Product> findByPriceBetween(@RequestParam Double from, @RequestParam Double to) {
        return productService.findByPriceBetween(from, to);
    }

    @GetMapping("/products/search/quantity/greater")
    public List<Product> findByQuantityGreaterThan(@RequestParam int quantity) {
        return productService.findByQuantityGreaterThan(quantity);
    }

    @GetMapping("/products/search/category")
    public List<Product> findByCategory(@RequestParam String category) {
        return productService.findByCategory(category);
    }
}