package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.UserRole;

public record RegisterDTO(

        String username,

        String password,

        UserRole role) {
}
