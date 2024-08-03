package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.PaymentRequest;
import com.bandeira.clinica_alves_oliveira.models.Payment;
import com.bandeira.clinica_alves_oliveira.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping
    public ResponseEntity<PaymentRequest> createPayment(@RequestBody @Valid PaymentRequest
                                                                      paymentRequest){
        var response = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/paymentsOfDay")
    public ResponseEntity<List<Payment>> findByDay(){
        var response = paymentService.findByDia();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/paymentsOfMonth")
    public ResponseEntity<List<Payment>> findByMonth(){
        var response = paymentService.findByMounth();
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
