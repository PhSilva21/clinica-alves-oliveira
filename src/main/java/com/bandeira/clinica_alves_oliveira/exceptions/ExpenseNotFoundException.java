package com.bandeira.clinica_alves_oliveira.exceptions;

public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(){
        super("Expense not found.");
    }

    public ExpenseNotFoundException(String message){
        super(message);
    }
}
