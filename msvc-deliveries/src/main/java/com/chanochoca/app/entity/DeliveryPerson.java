package com.chanochoca.app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("delivery_persons")
public class DeliveryPerson {

    @Id
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotEmpty
    private Boolean available;

    public DeliveryPerson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Phone number is required") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Phone number is required") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotEmpty Boolean getAvailable() {
        return available;
    }

    public void setAvailable(@NotEmpty Boolean available) {
        this.available = available;
    }
}
