package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.ExpenseType;
import com.bandeira.clinica_alves_oliveira.models.Origin;

public record UpdateExpenseDTO(

        ExpenseType expenseType,

        String name,

        Origin origin
) {
}
