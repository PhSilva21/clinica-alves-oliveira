package com.bandeira.clinica_alves_oliveira.models;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("user"),

    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
