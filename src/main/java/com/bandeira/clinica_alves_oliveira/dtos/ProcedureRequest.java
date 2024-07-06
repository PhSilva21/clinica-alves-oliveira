package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProcedureRequest(

        @NotNull(message = "The description cannot be null")
        @NotBlank(message = "The description cannot be null")
        String description,

        Specialty specialty,

        @NotNull(message = "The procedure cannot be null")
        @NotBlank(message = "The procedure cannot be null")
        Double value) {
}
