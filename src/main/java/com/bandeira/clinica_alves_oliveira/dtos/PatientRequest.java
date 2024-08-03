package com.bandeira.clinica_alves_oliveira.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientRequest(

        @NotNull(message = "The name cannot be null")
        String name,

        String cpf,

        String rg,

        LocalDate dateOfBirth,

        String cep,

        String number,

        String complement,

        String email,

        Integer cel,

        Integer tel,

        String responsible,

        String cpfOfResponsible) {
}
