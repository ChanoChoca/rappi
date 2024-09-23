package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("deliveries")
public class Delivery {

    @Id
    private Long id;

    @NotEmpty(message = "Order ID is required")
    private Long orderId;

    @NotEmpty(message = "Delivery person ID is required")
    private Long deliveryPersonId;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDate deliveryDate;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    public Delivery() {
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

    public @NotEmpty(message = "Delivery person ID is required") Long getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(@NotEmpty(message = "Delivery person ID is required") Long deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public @NotBlank(message = "Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status is required") String status) {
        this.status = status;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public @NotBlank(message = "Delivery address is required") String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(@NotBlank(message = "Delivery address is required") String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
