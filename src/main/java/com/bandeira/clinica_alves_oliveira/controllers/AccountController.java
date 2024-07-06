package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.services.AccountService;
import com.clinica.alvesoliveira.dto.AccountPaymentDTO;
import com.clinica.alvesoliveira.dto.AccountRequest;
import com.clinica.alvesoliveira.dto.UpdateAccountDTO;
import com.clinica.alvesoliveira.model.Account;
import com.clinica.alvesoliveira.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "contas")
public class AccountController {

    @Autowired
    private AccountService contaService;

    @PostMapping
    public ResponseEntity<AccountRequest> register(@RequestBody @Valid AccountRequest accountRequest){
        var response = contaService.createAccount(accountRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/debts")
    public List<Account> debts(){
        return contaService.debts();
    }

    @GetMapping("/pagamentosFuncionarios")
    public ResponseEntity<List<Account>> employwwPayments(@RequestParam @Param(("request")) LocalDate request){
        var response = contaService.employeePayments();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> pagarConta(@PathVariable ("id") Long id,
                                           @RequestBody @Valid AccountPaymentDTO accountPaymentDTO){
         contaService.low(id, accountPaymentDTO);
         return ResponseEntity.ok().build();
    }

    @GetMapping("/contasDoMes")
    public ResponseEntity<List<Account>> findByMes(@RequestParam @Param(("request")) LocalDate request){
        var response = contaService.findByMounth(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/contasPagas")
    public ResponseEntity<List<Account>> contasPagas(@RequestParam @Param(("request")) LocalDate request){
        var response = contaService.findByPaidBillsByMounth(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id, @RequestBody UpdateAccountDTO updateContaDTO){
        contaService.update(id, updateContaDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/voucher/{id}")
    public ResponseEntity<Account> setVoucher(@PathVariable Long id, @RequestParam("imagem") MultipartFile imagem){
        var response = contaService.insertVoucher(id, imagem);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/invoice/{id}")
    public ResponseEntity<Account> setInvoice(@PathVariable Long id, @RequestParam("imagem") MultipartFile imagem){
        var response = contaService.insertInvoice(id, imagem);
        return ResponseEntity.ok().body(response);
    }

}

