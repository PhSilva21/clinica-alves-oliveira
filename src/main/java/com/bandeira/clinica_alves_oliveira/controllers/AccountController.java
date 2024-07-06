package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.AccountPaymentDTO;
import com.bandeira.clinica_alves_oliveira.dtos.AccountRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateAccountDTO;
import com.bandeira.clinica_alves_oliveira.models.Account;
import com.bandeira.clinica_alves_oliveira.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<AccountRequest> register(@RequestBody @Valid AccountRequest accountRequest){
        var response = accountService.createAccount(accountRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/debts")
    public List<Account> debts(){
        return accountService.debts();
    }

    @GetMapping("/employeePayments")
    public ResponseEntity<List<Account>> employeePayments(@RequestParam @Param(("request")) LocalDate request){
        var response = accountService.employeePayments();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> paymentAccount(@PathVariable ("id") Long id,
                                           @RequestBody @Valid AccountPaymentDTO accountPaymentDTO){
         accountService.low(id, accountPaymentDTO);
         return ResponseEntity.ok().build();
    }

    @GetMapping("/findByMonth")
    public ResponseEntity<List<Account>> findByMonth(@RequestParam @Param(("request")) LocalDate request){
        var response = accountService.findByMounth(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/paidBills")
    public ResponseEntity<List<Account>> paidBills(@RequestParam @Param(("request")) LocalDate request){
        var response = accountService.findByPaidBillsByMounth(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id, @RequestBody UpdateAccountDTO updateContaDTO){
        accountService.update(id, updateContaDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/voucher/{id}")
    public ResponseEntity<Account> setVoucher(@PathVariable Long id, @RequestParam("imagem") MultipartFile imagem){
        var response = accountService.insertVoucher(id, imagem);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/invoice/{id}")
    public ResponseEntity<Account> setInvoice(@PathVariable Long id, @RequestParam("imagem") MultipartFile imagem){
        var response = accountService.insertInvoice(id, imagem);
        return ResponseEntity.ok().body(response);
    }

}

