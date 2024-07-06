package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.PaymentRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePaymentDTO;
import com.bandeira.clinica_alves_oliveira.models.Payment;
import com.bandeira.clinica_alves_oliveira.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "pagamento")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping
    public ResponseEntity<PaymentRequest> registerPayment(@RequestBody @Valid PaymentRequest
                                                                      paymentRequest){
        var response = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/paymentsOfDay")
    public ResponseEntity<List<Payment>> findByDay(){
        var response = paymentService.findByDia();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/paymentsOfMounth")
    public ResponseEntity<List<Payment>> findByMounth(){
        var response = paymentService.findByMounth();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updateById(@PathVariable Long id,
                                              @RequestBody UpdatePaymentDTO updatePaymentDTO){
        paymentService.update(id, updatePaymentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Long id){
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }
}