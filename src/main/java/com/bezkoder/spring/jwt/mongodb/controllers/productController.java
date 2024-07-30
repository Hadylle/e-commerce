package com.bezkoder.spring.jwt.mongodb.controllers;

import com.bezkoder.spring.jwt.mongodb.models.Product;
import com.bezkoder.spring.jwt.mongodb.payload.request.PageRequestDto;
import com.bezkoder.spring.jwt.mongodb.repository.ProductRepository;
import com.bezkoder.spring.jwt.mongodb.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/v1")
public class productController {

    @Autowired
    private ProductRepository productRepository;
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
    @GetMapping("/products/category/{name}")
    public List<Product> getProductsByCategory(@PathVariable String name) {
        return productService.getProductsByCategory(name);
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

    @PostMapping("/pageableProducts")
    public Page<Product> getAllProductsUsingPagination(@RequestBody PageRequestDto dto){
        Pageable pageable= new PageRequestDto().getPageable(dto);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage;
    }
    @PostMapping("/pageableList")
    public Page<Product> getAllProductsUsingPaginationList(@RequestBody PageRequestDto dto){

        List<Product> prouctList = productRepository.findAll();

        //1. PageListHolder
        PagedListHolder<Product> pagedListHolder = new PagedListHolder<Product>(prouctList);
        pagedListHolder.setPage(dto.getPageNo());
        pagedListHolder.setPageSize(dto.getPageSize());

        //2. PropertyComparator
        List<Product> pageSlice = pagedListHolder.getPageList();
        boolean ascending = dto.getSort().isAscending();
        PropertyComparator.sort(pageSlice, new MutableSortDefinition(dto.getSortByColumn(), true, true));
        //3. PageImpl
        Page<Product> products = new PageImpl<Product>(pageSlice, new PageRequestDto().getPageable(dto), prouctList.size());
    return products;
    }
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