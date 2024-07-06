package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.AccountType;
import com.bandeira.clinica_alves_oliveira.models.StatusAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AccountRequest(

         @NotNull(message = "The account type cannot be null")
         @NotBlank(message = "The account type cannot be empty")
         AccountType accountType,

         String identifier,

         @NotNull(message = "The account type cannot be null")
         @NotBlank(message = "The account type cannot be empty")
         StatusAccount statusAccount,

         LocalDate maturity,

         Double value,

         Double interest,

         Double total,

         @NotNull(message = "The account type cannot be null")
         @NotBlank(message = "The account type cannot be empty")
         String nameExpense) {
}
