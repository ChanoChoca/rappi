package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("deliveries")
public class Delivery {

    @Id
    private Long id;

    @NotNull
    private Long riderId;

    @NotNull
    private Long clientId;

    @NotNull
    private LocalDateTime deliveryTime;

    @NotNull
    @Size(min = 1, max = 50)
    private String status;

    public Delivery() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Long getRiderId() {
        return riderId;
    }

    public void setRiderId(@NotNull Long riderId) {
        this.riderId = riderId;
    }

    public @NotNull Long getClientId() {
        return clientId;
    }

    public void setClientId(@NotNull Long clientId) {
        this.clientId = clientId;
    }

    public @NotNull LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(@NotNull LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public @NotNull @Size(min = 1, max = 50) String getStatus() {
        return status;
    }

    public void setStatus(@NotNull @Size(min = 1, max = 50) String status) {
        this.status = status;
    }
}

