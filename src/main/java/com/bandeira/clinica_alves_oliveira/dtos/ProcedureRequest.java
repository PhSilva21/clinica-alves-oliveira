package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.Specialty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProcedureRequest(

        @NotNull(message = "The description cannot be null")
        String description,

        Specialty specialty,

        @NotNull(message = "The procedure cannot be null")
        BigDecimal value) {
}
