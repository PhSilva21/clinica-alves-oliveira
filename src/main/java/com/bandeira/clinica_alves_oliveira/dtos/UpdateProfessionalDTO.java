package com.bandeira.clinica_alves_oliveira.dtos;

import java.time.LocalDate;

public record UpdateProfessionalDTO(


        Long id,

        String name,

        LocalDate dateOfBirth,

        String rg,

        String cpf,

        String profession,

        String cep,

        String number,

        String complement,

        String email,

        Integer cel,

        Integer tel,

        String bank,

        String agency,

        String account
){
}
