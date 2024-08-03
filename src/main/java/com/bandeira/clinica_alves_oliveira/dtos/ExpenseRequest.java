package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.ExpenseType;
import com.bandeira.clinica_alves_oliveira.models.Origin;
import jakarta.validation.constraints.NotNull;

public record ExpenseRequest(

        @NotNull(message = "The expense cannot be null")
        ExpenseType expenseType,

        String name,

        Origin origin) {
}
