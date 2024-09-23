package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("delivery_vehicles")
public class DeliveryVehicle {

    @Id
    private Long id;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotBlank(message = "License plate is required")
    private String licensePlate;

    private Long deliveryPersonId;

    public DeliveryVehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Vehicle type is required") String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(@NotBlank(message = "Vehicle type is required") String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public @NotBlank(message = "License plate is required") String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NotBlank(message = "License plate is required") String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(Long deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }
}
