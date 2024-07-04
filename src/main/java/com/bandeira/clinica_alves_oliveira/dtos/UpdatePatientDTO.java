package com.bandeira.clinica_alves_oliveira.dtos;

import java.time.LocalDate;

public record UpdatePatientDTO(

        String name,

        String cpf,

        String rg,

        LocalDate dateOfBirth,

        String cep,

        String state,

        String city,

        String street,

        String number,

        String neighborhood,

        String complement,

        String email,

        Integer cel,

        Integer tel,

        String responsible,

        String cpfOfResponsible,

        Double outstandingBalance,

        Double valueUsed,

        Double amountReceived
) {
}
