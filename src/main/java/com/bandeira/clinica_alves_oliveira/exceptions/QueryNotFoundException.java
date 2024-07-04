package com.bandeira.clinica_alves_oliveira.exceptions;

public class QueryNotFoundException extends RuntimeException{

    public QueryNotFoundException(){
        super("Query not found");
    }

    public QueryNotFoundException(String message){
        super(message);
    }
}
