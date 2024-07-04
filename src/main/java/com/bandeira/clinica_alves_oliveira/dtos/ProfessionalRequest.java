package com.bandeira.clinica_alves_oliveira.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProfessionalRequest(

        @NotNull@NotNull(message = "The name cannot be null")
        @NotBlank(message = "The name cannot be null")
        @NotBlank
        String name,

        LocalDate dateOfBirth,

        String rg,

        String cpf,

        @NotNull(message = "The profession cannot be null")
        @NotBlank(message = "The profession cannot be null")
        @NotBlank
        String profession,

        String cep,

        String number,

        String complement,

        String email,

        Integer cel,

        Integer tel,

        String bank,

        String agency,

        String account) {
}
