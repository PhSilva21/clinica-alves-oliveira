package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.AccountType;
import com.bandeira.clinica_alves_oliveira.models.FormOfPayment;
import com.bandeira.clinica_alves_oliveira.models.StatusAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateAccountDTO(

    AccountType accountType,

    String identifier,

    StatusAccount statusAccount,

    LocalDate maturity,

    Double value,

    Double interest,

    Double total,

    String nameExpense,

    FormOfPayment formOfPayment,

    LocalDateTime datePayment) {
}
