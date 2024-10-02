package com.chanochoca.app.entity.models;

import com.chanochoca.app.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table("orders")
public class Order {

    @Id
    private Long id;

    private String userId;

    @NotEmpty(message = "Order date is required")
    private LocalDate orderDate;

    @Positive(message = "Total price must be greater than zero")
    private BigDecimal totalPrice;

    @NotBlank(message = "Status is required")
    private String status;

    @Transient
    private List<OrderProduct> orderProducts;

    @Transient
    private List<Product> products;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public void addOrderProduct(OrderProduct orderProduct) {
        if (orderProducts == null) {
            orderProducts = new ArrayList<>();
        }
        this.orderProducts.add(orderProduct);
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.remove(orderProduct);
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
