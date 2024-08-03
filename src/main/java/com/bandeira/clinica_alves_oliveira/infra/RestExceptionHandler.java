package com.bandeira.clinica_alves_oliveira.infra;

import com.bandeira.clinica_alves_oliveira.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(PatientNotFoundException.class)
    private ResponseEntity<String> patientNotFound(PatientNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
    }

    @ExceptionHandler(ProfessionalNotFoundException.class)
    private ResponseEntity<String> professionalNotFound(ProfessionalNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professional not found.");
    }

    @ExceptionHandler(QueryNotFoundException.class)
    private ResponseEntity<String> queryNotFound(QueryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Query not found.");
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    private ResponseEntity<String> expenseNotFound(ExpenseNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
    }

    @ExceptionHandler(AccountNotFoundException.class)
    private ResponseEntity<String> accountNotFound(AccountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
    }

    @ExceptionHandler(ProcedureNotFoundException.class)
    private ResponseEntity<String> procedureNotFound(ProcedureNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Procedure not found.");
    }

    @ExceptionHandler(BudgetNotFoundException.class)
    private ResponseEntity<String> budgetNotFound(BudgetNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Budget not found.");
    }

    @ExceptionHandler(OutstandingBalanceException.class)
    private ResponseEntity<String> outstandigBalanceException(OutstandingBalanceException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The payment amount is" +
                " greater than the patient's outstanding balance.");
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    private ResponseEntity<String> paymentNotFound(PaymentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
    }

    @ExceptionHandler(AddressNotFoundException.class)
    private ResponseEntity<String> addressNotFound(AddressNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found.");
    }


}

