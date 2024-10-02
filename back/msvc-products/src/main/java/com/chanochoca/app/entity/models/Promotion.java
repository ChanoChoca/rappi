package com.chanochoca.app.entity.models;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table("promotions")
public class Promotion {

    @Id
    private Long id;

    @NotBlank(message = "Promotion name is required")
    private String name;

    @Positive(message = "Discount percentage must be positive")
    private BigDecimal discountPercentage;

    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDate endDate;

    @Transient
    private List<PromotionProduct> promotionProducts; // Reference to products

    @Transient
    private List<Product> products;

    public Promotion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Promotion name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Promotion name is required") String name) {
        this.name = name;
    }

    public @Positive(message = "Discount percentage must be positive") BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(@Positive(message = "Discount percentage must be positive") BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public @FutureOrPresent(message = "Start date must be in the present or future") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@FutureOrPresent(message = "Start date must be in the present or future") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @FutureOrPresent(message = "End date must be in the present or future") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@FutureOrPresent(message = "End date must be in the present or future") LocalDate endDate) {
        this.endDate = endDate;
    }

    public void addPromotionProduct(PromotionProduct promotionProduct) {
        if (promotionProducts == null) {
            promotionProducts = new ArrayList<>();
        }
        this.promotionProducts.add(promotionProduct);
    }

    public void removePromotionProduct(PromotionProduct promotionProduct) {
        this.promotionProducts.remove(promotionProduct);
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }

    public void setPromotionProducts(List<PromotionProduct> promotionProducts) {
        this.promotionProducts = promotionProducts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
