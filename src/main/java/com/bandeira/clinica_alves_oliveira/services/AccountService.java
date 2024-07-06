package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.AccountPaymentDTO;
import com.bandeira.clinica_alves_oliveira.dtos.AccountRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateAccountDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.AccountNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ExpenseNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Account;
import com.bandeira.clinica_alves_oliveira.models.AccountType;
import com.bandeira.clinica_alves_oliveira.models.Expense;
import com.bandeira.clinica_alves_oliveira.models.StatusAccount;
import com.bandeira.clinica_alves_oliveira.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UploadService uploadService;



    public AccountRequest createAccount(AccountRequest accountRequest){
        Expense expense = expenseService.findByName(accountRequest.nameExpense());

        if(expense == null){
            throw new ExpenseNotFoundException();
        }

        Account account = new Account(
                accountRequest.accountType(),
                accountRequest.identifier(),
                accountRequest.statusAccount(),
                accountRequest.maturity(),
                accountRequest.value(),
                accountRequest.interest(),
                accountRequest.total(),
                accountRequest.nameExpense(),
                expense
                );


        accountRepository.save(account);

        return accountRequest;
    }

    public Account insertVoucher(Long id, MultipartFile image){

        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        if(account == null){
            throw  new AccountNotFoundException();
        }

        uploadService.uploadImage(image);

        account.setVoucherFile(image.getOriginalFilename());

        accountRepository.save(account);

        return account;
    }

    public Account insertInvoice(Long id, MultipartFile image){

        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        if(account == null){
            throw  new AccountNotFoundException();
        }

        uploadService.uploadImage(image);

        account.setInvoiceFile(image.getOriginalFilename());

        accountRepository.save(account);

        return account;
    }


    public List<Account> findAll(){

        Sort sort = Sort.by("maturity").ascending();

        return accountRepository.findAll(sort);
    }


    public List<Account> debts(){
        return findAll().stream().filter(c -> c.getStatusAccount()
                        .equals(StatusAccount.PENDING))
                        .collect(Collectors.toList());
    }

    public List<Account> employeePayments(){
        return findAll().stream().filter(c -> c.getAccountType()
                        .equals(AccountType.PROFESSIONAL))
                        .collect(Collectors.toList());
    }

    public List<Account> findByPaidBillsByMounth(LocalDate date){
        return findAll().stream().filter(c -> c.getStatusAccount()
                        .equals(StatusAccount.PAID_OUT) && c.getMaturity().getMonth().equals(date.getMonth()))
                        .collect(Collectors.toList());
    }

    public List<Account> findByMounth(LocalDate date){
        return findAll().stream().filter(c -> c.getMaturity().getMonth()
                .equals(date.getMonth()))
                .collect(Collectors.toList());
    }


    public void low(Long id, AccountPaymentDTO accountPaymentDTO){
          var account = findById(id).orElseThrow(AccountNotFoundException::new);

          account.setInterest(accountPaymentDTO.interest());
          account.setTotal(account.getValue() + accountPaymentDTO.interest());
          account.setFormOfPayment(accountPaymentDTO.formOfPayment());
          account.setPaymentDate(accountPaymentDTO.datePayment());
          account.setStatusAccount(StatusAccount.PAID_OUT);

          accountRepository.save(account);
    }


    public void update(Long id, UpdateAccountDTO updateAccountDTO) {

        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        account.setAccountType(updateAccountDTO.accountType());
        account.setIdentifier(updateAccountDTO.identifier());
        account.setStatusAccount(updateAccountDTO.statusAccount());
        account.setMaturity(updateAccountDTO.maturity());
        account.setValue(updateAccountDTO.value());
        account.setInterest(updateAccountDTO.interest());
        account.setTotal(updateAccountDTO.total());
        account.setNameExpense(updateAccountDTO.nameExpense());

        var expense = expenseService.findByName(updateAccountDTO.nameExpense());

        if(expense == null){
            throw new ExpenseNotFoundException();
        }

        account.setExpense(expense);
        account.setFormOfPayment(updateAccountDTO.formOfPayment());
        account.setPaymentDate(updateAccountDTO.datePayment());


        accountRepository.save(account);
    }


    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }


    public void deleteById(Long id) {

      accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        accountRepository.deleteById(id);
    }

}
