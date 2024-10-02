package com.chanochoca.app.entity.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("products")
public class Product {

    @Id
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotEmpty(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotEmpty
    private Boolean available;

    private Long categoryId;

    @Positive(message = "Stock must be positive")
    private Integer stock;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String name) {
        this.name = name;
    }

    public @Size(max = 255, message = "Description must not exceed 255 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 255, message = "Description must not exceed 255 characters") String description) {
        this.description = description;
    }

    public @NotEmpty(message = "Price is required") @Positive(message = "Price must be greater than zero") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotEmpty(message = "Price is required") @Positive(message = "Price must be greater than zero") BigDecimal price) {
        this.price = price;
    }

    public @NotEmpty Boolean getAvailable() {
        return available;
    }

    public void setAvailable(@NotEmpty Boolean available) {
        this.available = available;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public @Positive(message = "Stock must be positive") Integer getStock() {
        return stock;
    }

    public void setStock(@Positive(message = "Stock must be positive") Integer stock) {
        this.stock = stock;
    }
}
