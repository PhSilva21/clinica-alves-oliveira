package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.AccountPaymentDTO;
import com.bandeira.clinica_alves_oliveira.dtos.AccountRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateAccountDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.AccountNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ExpenseNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.*;
import com.bandeira.clinica_alves_oliveira.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ExpenseService expenseService;

    @Mock
    UploadService uploadUtil;

    @Captor
    ArgumentCaptor<Account> accountArgumentCaptor;

    UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO(
            AccountType.SUPPLIER_EXPENSE,
            "Descartaveis",
            StatusAccount.PENDING,
            LocalDate.of(2024,07,22),
            64.00,
            0.0,
            0.0,
            "Dental",
            FormOfPayment.PIX,
            LocalDateTime.of(17,02,22,13,53,28,9129)
    );

    AccountPaymentDTO accountPaymentDTO = new AccountPaymentDTO(
            64.00,
            0.0,
            FormOfPayment.PIX,
            LocalDateTime.now()
    );


    Expense expense = new Expense(
            ExpenseType.PRODUCT_SUPPLIER,
            "Dental",
            Origin.FRIEND);

    AccountRequest accountRequest = new AccountRequest(
            AccountType.SUPPLIER_EXPENSE,
            "Descartaveis",
            StatusAccount.PENDING,
            LocalDate.of(2024,04,13),
            64.00,
            0.0,
            0.0,
            "Dental"
    );

    Account account =  new Account(
            AccountType.PROFESSIONAL,
            "Descartaveis",
            StatusAccount.PENDING,
            LocalDate.of(2024,07,22),
            64.00,
            0.0,
            0.0,
            "Dental",
            expense
    );

    @Nested
    class createAccount{

        @Test
        @DisplayName("Must create account successfully")
        void MustCreateAccountSuccessfully() {
            doReturn(expense)
                    .when(expenseService)
                    .findByName(accountRequest.nameExpense());
            doReturn(account)
                    .when(accountRepository)
                    .save(accountArgumentCaptor.capture());

            var response = accountService.createAccount(accountRequest);

            assertNotNull(response);

            var accountCaptured = accountArgumentCaptor.getValue();

            assertEquals(accountRequest.accountType(), accountCaptured.getAccountType());
            assertEquals(accountRequest.identifier(), accountCaptured.getIdentifier());
            assertEquals(accountRequest.statusAccount(), accountCaptured.getStatusAccount());
            assertEquals(accountRequest.maturity(), accountCaptured.getMaturity());
            assertEquals(accountRequest.value(), accountCaptured.getValue());
            assertEquals(accountRequest.interest(), accountCaptured.getInterest());
            assertEquals(accountRequest.total(), accountCaptured.getTotal());
            assertEquals(accountRequest.nameExpense(), accountCaptured.getNameExpense());
            assertEquals(expense, accountCaptured.getExpense());
            assertEquals(account.getVoucherFile(), accountCaptured.getVoucherFile());
            assertEquals(account.getInvoiceFile(), accountCaptured.getInvoiceFile());
        }


        @Test
        @DisplayName("Must launch a ExpenseNotFoundException")
        void MustLaunchAExpenseNotFound() {
            doThrow(new ExpenseNotFoundException())
                    .when(expenseService)
                    .findByName(any());


            assertThrows(ExpenseNotFoundException.class, () -> accountService.createAccount(accountRequest));
        }
    }

    @Nested
    class insertVoucher {

        @Test
        @DisplayName("Must insert voucher successfully")
        void MustInsertVoucherSuccessfully() throws IOException {
            File file = new File("C:\\Users\\pedro_jdm\\Downloads\\Files\\images.png");
            InputStream stream = new FileInputStream(file);
            MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());
            doReturn(true)
                    .when(uploadUtil)
                    .uploadImage(multipartFileToSend);

            var response = accountService.insertVoucher(account.getId(), multipartFileToSend);

            account.setVoucherFile(multipartFileToSend.getName());

            assertEquals(account.getVoucherFile(), response.getVoucherFile());
        }

        @Test
        @DisplayName("Should throw exception when not finding the account")
        void ShouldThrowExceptionWhenNotFindingTheAccount() throws IOException {
            File file = new File("C:\\Users\\pedro_jdm\\Downloads\\Files\\images.png");
            InputStream stream = new FileInputStream(file);
            MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

            doReturn(Optional.empty())
                    .when(accountRepository)
                    .findById(account.getId());

            assertThrows(AccountNotFoundException.class, () -> accountService
                    .insertVoucher(account.getId(), multipartFileToSend));

            verify(accountRepository, times(1)).findById(account.getId());
            verify(accountRepository, times(0)).save(account);
        }
    }

    @Nested
    class insertInvoice {

        @Test
        @DisplayName("Must successfully insert invoice")
        void MustSuccessfullyInsertInvoice() throws IOException {
            File file = new File("C:\\Users\\pedro_jdm\\Downloads\\Files\\images.png");
            InputStream stream = new FileInputStream(file);
            MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());
            doReturn(true)
                    .when(uploadUtil)
                    .uploadImage(multipartFileToSend);

            var response = accountService.insertInvoice(account.getId(), multipartFileToSend);

            account.setInvoiceFile(multipartFileToSend.getName());

            assertEquals(account.getVoucherFile(), response.getVoucherFile());
        }

        @Test
        @DisplayName("Should throw exception when not finding the account")
        void ShouldThrowExceptionWhenNotFindingTheAccount() throws IOException {
            File file = new File("C:\\Users\\pedro_jdm\\Downloads\\Files\\images.png");
            InputStream stream = new FileInputStream(file);
            MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

            doReturn(Optional.empty())
                    .when(accountRepository)
                    .findById(account.getId());

            assertThrows(AccountNotFoundException.class, () -> accountService
                    .insertInvoice(account.getId(), multipartFileToSend));

            verify(accountRepository, times(1)).findById(account.getId());
            verify(accountRepository, times(0)).save(account);
        }
    }

    @Nested
    class debts {

        @Test
        @DisplayName("Must return the debts")
        void MustReturnTheDebts() {
            var accountList = List.of(account);

            Sort sort = Sort.by("maturity").ascending();

            doReturn(accountList)
                    .when(accountRepository)
                    .findAll(sort);

            var fielterList = accountList.stream().filter(c -> c.getStatusAccount()
                            .equals(StatusAccount.PENDING))
                    .toList();

            var response = accountService.debts();

            assertNotNull(response);
            assertEquals(fielterList.size(), response.size());
        }
    }

    @Nested
    class employeePayments {

        @Test
        @DisplayName("Must return employee payments")
        void MustReturnEmployeePayments() {
            var accountList = List.of(account);

            Sort sort = Sort.by("maturity").ascending();

            doReturn(accountList)
                    .when(accountRepository)
                    .findAll(sort);

            var filterList = accountList.stream().filter(c -> c.getAccountType()
                            .equals(AccountType.PROFESSIONAL))
                    .toList();

            var response = accountService.employeePayments();

            assertNotNull(response);
            assertEquals(response.size(), filterList.size());
        }
    }

    @Nested
    class findByPaidBillsByMounth {

        @Test
        @DisplayName("Must return paid bills")
        void MustReturnPaidBills() {
            var accountList = List.of(account);

            Sort sort = Sort.by("maturity").ascending();

            doReturn(accountList)
                    .when(accountRepository)
                    .findAll(sort);

            account.setStatusAccount(StatusAccount.PAID_OUT);

            var request = LocalDate.of(2024, 07, 14);

            var filterList = accountList.stream().filter(c -> c.getStatusAccount()
                            .equals(StatusAccount.PAID_OUT) && c.getMaturity().getMonth().equals(request.getMonth()))
                    .toList();

            var response = accountService.findByPaidBillsByMounth(request);

            assertNotNull(response);
            assertEquals(response.size(), filterList.size());
        }
    }

    @Nested
    class findByMounth {

        @Test
        @DisplayName("Must return the monthly accounts according to the parameter entered")
        void MustReturnTheMonthlyAccountsAccordingToTheParameterEntered() {
            var accountList = List.of(account);

            Sort sort = Sort.by("maturity").ascending();

            doReturn(accountList)
                    .when(accountRepository)
                    .findAll(sort);

            var request = LocalDate.of(2024, 07, 04);

            var filterList = accountList.stream().filter(c -> c.getMaturity().getMonth()
                            .equals(request.getMonth()))
                    .toList();

            var response = accountService.findByMounth(request);

            assertNotNull(response);
            assertEquals(filterList.size(), response.size());
        }
    }

    @Nested
    class low {

        @Test
        @DisplayName("Must pay the bill successfully")
        void MustPayTheBillSuccessfully() {
            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());
            doReturn(account)
                    .when(accountRepository)
                    .save(accountArgumentCaptor.capture());

            accountService.low(account.getId(), accountPaymentDTO);

            var accountCaptured = accountArgumentCaptor.getValue();

            assertEquals(account.getId(), accountCaptured.getId());
            assertEquals(accountPaymentDTO.value(), accountCaptured.getValue());
            assertEquals(accountPaymentDTO.interest(), accountCaptured.getInterest());
            assertEquals(accountPaymentDTO.formOfPayment(), accountCaptured.getFormOfPayment());
            assertEquals(accountPaymentDTO.datePayment(), accountCaptured.getPaymentDate());
            assertEquals(account.getStatusAccount(), StatusAccount.PAID_OUT);
        }

        @Test
        @DisplayName("Should throw exception when not finding the account")
        void ShouldThrowExceptionWhenNotFindingTheAccount() {
            doReturn(Optional.empty())
                    .when(accountRepository)
                    .findById(account.getId());

            assertThrows(AccountNotFoundException.class,
                    () -> accountService.low(account.getId(), accountPaymentDTO));

            verify(accountRepository, times(1)).findById(account.getId());
            verify(accountRepository, times(0)).save(account);
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Must update account successfully")
        void MustUpdateAccountSuccessfully() {
            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());
            doReturn(expense)
                    .when(expenseService)
                    .findByName(expense.getName());
            doReturn(account)
                    .when(accountRepository)
                    .save(accountArgumentCaptor.capture());

            accountService.update(account.getId(), updateAccountDTO);

            var accountCaptured = accountArgumentCaptor.getValue();

            assertEquals(updateAccountDTO.accountType(), accountCaptured.getAccountType());
            assertEquals(updateAccountDTO.identifier(), accountCaptured.getIdentifier());
            assertEquals(updateAccountDTO.statusAccount(), accountCaptured.getStatusAccount());
            assertEquals(updateAccountDTO.maturity(), accountCaptured.getMaturity());
            assertEquals(updateAccountDTO.value(), accountCaptured.getValue());
            assertEquals(updateAccountDTO.interest(), accountCaptured.getInterest());
            assertEquals(updateAccountDTO.total(), accountCaptured.getTotal());
            assertEquals(updateAccountDTO.nameExpense(), accountCaptured.getNameExpense());
            assertEquals(expense, accountCaptured.getExpense());
            assertEquals(account.getVoucherFile(), accountCaptured.getVoucherFile());
            assertEquals(account.getInvoiceFile(), accountCaptured.getInvoiceFile());

            verify(accountRepository, times(1))
                    .findById(account.getId());
            verify(expenseService, times(1))
                    .findByName(account.getNameExpense());
            verify(accountRepository, times(1))
                    .save(account);
        }

        @Test
        @DisplayName("Must throw an exception when the account cannot be found")
        void MustThrowAnExceptionWhenTheAccountCannotBeFound() {
            doReturn(Optional.empty())
                    .when(accountRepository)
                    .findById(account.getId());


            assertThrows(AccountNotFoundException.class, () -> accountService.update(account.getId(), updateAccountDTO));

            verify(accountRepository, times(1)).findById(account.getId());
            verify(expenseService, times(0)).findByName(updateAccountDTO.nameExpense());
            verify(accountRepository, times(0)).save(account);
        }


        @Test
        @DisplayName("Must throw an exception when the expense cannot be found")
        void MustThrowAnExceptionWhenTheExpenseCannotBeFound() {
            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());
            doThrow(new ExpenseNotFoundException())
                    .when(expenseService)
                    .findByName(any());


            assertThrows(ExpenseNotFoundException.class, () -> accountService.update(account.getId(), updateAccountDTO));

            verify(accountRepository, times(1)).findById(account.getId());
            verify(expenseService, times(1)).findByName(updateAccountDTO.nameExpense());
            verify(accountRepository, times(0)).save(account);
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Must return the account successfully")
        void MustReturnTheAccountSuccessfully() {
            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());

            var response = accountService.findById(account.getId());

            assertTrue(response.isPresent());
            assertEquals(account.getId(), response.get().getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding the account")
        void ShouldThrowExceptionWhenNotFindingTheAccount() {
            doReturn(Optional.empty())
                    .when(accountRepository)
                    .findById(account.getId());

            var response = accountService.findById(account.getId());

            assertTrue(response.isEmpty());

            verify(accountRepository, times(1)).findById(account.getId());
            verify(expenseService, times(0)).deleteById(account.getId());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete the account successfully")
        void MustDeleteTheAccountSuccessfully() {
            doReturn(Optional.of(account))
                    .when(accountRepository)
                    .findById(account.getId());
            doNothing()
                    .when(accountRepository)
                    .deleteById(account.getId());

            accountService.deleteById(account.getId());

            verify(accountRepository, times(1))
                    .findById(account.getId());
            verify(accountRepository, times(1))
                    .deleteById(account.getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding the account")
        void ShouldThrowExceptionWhenNotFindingTheAccount() {
            doThrow(new AccountNotFoundException())
                    .when(accountRepository)
                    .findById(any());

            assertThrows(AccountNotFoundException.class, () -> accountService.deleteById(account.getId()));
        }
    }
}