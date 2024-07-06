package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.StatusBudget;

import java.time.LocalDate;

public record UpdateBudgetDTO(

         LocalDate dateRegister,

         StatusBudget statusBudget,

         String namePatient,

         String nameProfessional,

         String nameProcedure
) {
}
