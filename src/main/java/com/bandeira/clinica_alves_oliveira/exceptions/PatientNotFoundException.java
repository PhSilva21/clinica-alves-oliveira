package com.bandeira.clinica_alves_oliveira.exceptions;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(){
        super("Patient not found");
    }

    public PatientNotFoundException(String message){
        super(message);
    }
}
