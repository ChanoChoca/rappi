package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("payments")
public class Payment {

    @Id
    private Long id;

    @NotEmpty(message = "Order ID is required")
    private Long orderId;

    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private LocalDate paymentDate;

    @NotBlank(message = "Status is required")
    private String status;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Order ID is required") Long getOrderId() {
        return orderId;
    }

    public void setOrderId(@NotEmpty(message = "Order ID is required") Long orderId) {
        this.orderId = orderId;
    }

    public @Positive(message = "Amount must be greater than zero") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@Positive(message = "Amount must be greater than zero") BigDecimal amount) {
        this.amount = amount;
    }

    public @NotBlank(message = "Payment method is required") String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(@NotBlank(message = "Payment method is required") String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public @NotBlank(message = "Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status is required") String status) {
        this.status = status;
    }
}
