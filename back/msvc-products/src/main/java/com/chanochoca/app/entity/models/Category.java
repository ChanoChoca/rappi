package com.chanochoca.app.entity.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
public class Category {

    @Id
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String name) {
        this.name = name;
    }

    public @Size(max = 255, message = "Description must not exceed 255 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 255, message = "Description must not exceed 255 characters") String description) {
        this.description = description;
    }
}
