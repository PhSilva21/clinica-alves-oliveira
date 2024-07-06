package com.bandeira.clinica_alves_oliveira.exceptions;

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(){
        super("Payment not found");
    }

    public PaymentNotFoundException(String message){
        super(message);
    }
}
