package com.bandeira.clinica_alves_oliveira.exceptions;

public class OutstandingBalanceException extends RuntimeException{

    public OutstandingBalanceException(){
        super("The payment amount is greater than the patient's outstanding balance");
    }

    public OutstandingBalanceException(String message){
        super(message);
    }
}
