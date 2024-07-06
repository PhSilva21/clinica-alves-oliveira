package com.bandeira.clinica_alves_oliveira.exceptions;

public class ProcedureNotFoundException extends RuntimeException {

    public ProcedureNotFoundException(){
        super("Procedure not found.");
    }

    public ProcedureNotFoundException(String message){
        super(message);
    }
}
