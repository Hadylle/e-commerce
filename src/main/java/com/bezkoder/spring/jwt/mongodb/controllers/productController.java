package com.bezkoder.spring.jwt.mongodb.controllers;

import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class productController {

    private final ProductService productService;

    public productController(ProductService productService) {
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
        return product.map(ResponseEntity ::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/Category/{category_id}")
    public List<Product> getProductsByCategory(@PathVariable Integer category_id) {
        return productService.getProductsByCategory(category_id);
    }
    @PutMapping("/product/{product_id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer product_id, @RequestBody Product product){
        Product updatedProduct = productService.updateProduct(product_id,product);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/products/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProduct(product_id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
