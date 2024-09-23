package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("order_items")
public class OrderItem {

    @Id
    private Long id;

    @NotEmpty(message = "Product ID is required")
    private Long productId;

    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Product ID is required") Long getProductId() {
        return productId;
    }

    public void setProductId(@NotEmpty(message = "Product ID is required") Long productId) {
        this.productId = productId;
    }

    public @Positive(message = "Quantity must be greater than zero") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Positive(message = "Quantity must be greater than zero") Integer quantity) {
        this.quantity = quantity;
    }

    public @Positive(message = "Price must be greater than zero") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "Price must be greater than zero") BigDecimal price) {
        this.price = price;
    }
}
