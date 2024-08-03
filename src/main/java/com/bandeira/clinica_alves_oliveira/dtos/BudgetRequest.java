package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.StatusBudget;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BudgetRequest(

        @NotNull(message = "The date cannot be null")
        LocalDate dateRegister,

        @NotNull(message = "The status budget cannot be null")
        StatusBudget statusBudget,

        @NotNull(message = "The patient cannot be null")
        String namePatient,

        String nameProfessional,


        @NotNull(message = "The procedure cannot be null")
        String nameProcedure) {
}
