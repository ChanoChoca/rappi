package com.chanochoca.app.models;

import com.chanochoca.app.models.entity.Authority;

import java.util.Set;

public class UserAuthorityDTO {
    private Set<Authority> authorities;

    // Getters y Setters
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
