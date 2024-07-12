package com.bezkoder.spring.jwt.mongodb.models;

import com.bezkoder.spring.jwt.mongodb.CategoryDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private Integer product_id;  // Changed to String for MongoDB

    private String name;
    private String description;
    private Double price;
    private String imageURL;
    private int quantity;

    @DBRef(lazy = true)
    @JsonDeserialize(using = CategoryDeserializer.class)
    private Category category;

    public Product() {
    }

    public Product(Integer product_id, String name, String description, Double price, String imageURL, int quantity, Category category) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                ", quantity=" + quantity +
                ", category=" + category +
                '}';
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
