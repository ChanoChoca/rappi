package com.chanochoca.app.entity.models;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("addresses")
public class Address {

    @Id
    private Long id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Street is required") String getStreet() {
        return street;
    }

    public void setStreet(@NotBlank(message = "Street is required") String street) {
        this.street = street;
    }

    public @NotBlank(message = "City is required") String getCity() {
        return city;
    }

    public void setCity(@NotBlank(message = "City is required") String city) {
        this.city = city;
    }

    public @NotBlank(message = "Postal code is required") String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@NotBlank(message = "Postal code is required") String postalCode) {
        this.postalCode = postalCode;
    }

    public @NotBlank(message = "Country is required") String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank(message = "Country is required") String country) {
        this.country = country;
    }
}
