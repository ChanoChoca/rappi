package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("packages")
public class Package {

    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    @Positive
    private Double weight;

    @NotNull
    private Long deliveryId;

    public Package() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(min = 1, max = 255) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @Size(min = 1, max = 255) String description) {
        this.description = description;
    }

    public @NotNull @Positive Double getWeight() {
        return weight;
    }

    public void setWeight(@NotNull @Positive Double weight) {
        this.weight = weight;
    }

    public @NotNull Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(@NotNull Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
