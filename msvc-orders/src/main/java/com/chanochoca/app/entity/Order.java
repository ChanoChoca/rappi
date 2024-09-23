package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Table("orders")
public class Order {

    @Id
    private Long id;

    @NotEmpty(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Order date is required")
    private LocalDate orderDate;

    @Positive(message = "Total price must be greater than zero")
    private BigDecimal totalPrice;

    @NotBlank(message = "Status is required")
    private String status;

    private List<OrderItem> items;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "User ID is required") Long getUserId() {
        return userId;
    }

    public void setUserId(@NotEmpty(message = "User ID is required") Long userId) {
        this.userId = userId;
    }

    public @NotEmpty(message = "Order date is required") LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(@NotEmpty(message = "Order date is required") LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public @Positive(message = "Total price must be greater than zero") BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@Positive(message = "Total price must be greater than zero") BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public @NotBlank(message = "Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status is required") String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
