package com.bandeira.clinica_alves_oliveira.exceptions;

public class ProfessionalNotFoundException extends RuntimeException {

    public ProfessionalNotFoundException() {
        super("Professional not found.");
    }

    public ProfessionalNotFoundException(String message){
        super(message);
    }

}
