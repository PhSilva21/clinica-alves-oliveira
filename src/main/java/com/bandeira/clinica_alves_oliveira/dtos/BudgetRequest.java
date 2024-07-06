package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.StatusBudget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BudgetRequest(

        @NotNull(message = "The date cannot be null")
        @NotBlank(message = "The date cannot be empty")
        LocalDate dateRegister,

        @NotNull(message = "The status budget cannot be null")
        @NotBlank(message = "The status budget cannot be empty")
        StatusBudget statusBudget,

        @NotNull(message = "The patient cannot be null")
        @NotBlank(message = "The patient cannot be empty")
        String namePatient,

        String nameProfessional,


        @NotNull(message = "The procedure cannot be null")
        @NotBlank(message = "The procedure cannot be empty")
        String nameProcedure) {
}
