package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.ExpenseRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateExpenseDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.ExpenseNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Expense;
import com.bandeira.clinica_alves_oliveira.models.ExpenseType;
import com.bandeira.clinica_alves_oliveira.models.Origin;
import com.bandeira.clinica_alves_oliveira.repositories.ExpenseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @InjectMocks
    ExpenseService expenseService;

    @Mock
    ExpenseRepository expenseRepository;

    @Captor
    ArgumentCaptor<Expense> expenseArgumentCaptor;

    Expense expense = new Expense(
            ExpenseType.PEOPLE,
            "Dental",
            Origin.OTHERS
    );

    UpdateExpenseDTO updateExpenseDTO = new UpdateExpenseDTO(
            ExpenseType.VARIABLE_EXPENSES,
            "Agua",
            Origin.OTHERS
    );

    ExpenseRequest expenseRequest = new ExpenseRequest(
            ExpenseType.TAX,
            "CRO",
            Origin.FRIEND
    );

    @Nested
    class crateExpense {

        @Test
        @DisplayName("Must create expense successfully")
        void MustCreateExpenseSuccessfully() {
            doReturn(expense)
                    .when(expenseRepository)
                    .save(expenseArgumentCaptor.capture());

            var response = expenseService.createExpense(expenseRequest);

            assertNotNull(response);

            var expenseCaptured = expenseArgumentCaptor.getValue();

            assertEquals(expenseRequest.expenseType(), expenseCaptured.getExpenseType());
            assertEquals(expenseRequest.name(), expenseCaptured.getName());
            assertEquals(expenseRequest.origin(), expenseCaptured.getOrigin());
        }
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Must return expense successfully")
        void MustReturnExpenseSuccessfully() {
            doReturn(Optional.of(expense))
                    .when(expenseRepository)
                    .findById(expense.getId());

            var response = expenseService.findById(expense.getId());

            assertTrue(response.isPresent());
            assertEquals(expense.getId(), response.get().getId());
        }

        @Test
        @DisplayName("Must throw an exception when do not find an expense")
        void MustThrowAnExceptionWhenDoNotFindAnExpense(){
            doReturn(Optional.empty())
                    .when(expenseRepository)
                    .findById(expense.getId());

            var response = expenseService.findById(expense.getId());

            assertTrue(response.isEmpty());

            verify(expenseRepository, times(1))
                    .findById(expense.getId());
        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("Must return expenses successfully")
        void MustReturnExpensesSuccessfully() {
            var expenseList = List.of(expense);
            doReturn(expenseList)
                    .when(expenseRepository)
                    .findAll();

            var response = expenseService.findAll();

            assertNotNull(response);
            assertEquals(expenseList.size(), response.size());
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Must update expense successfully")
        void MustUpdateExpenseSuccessfully() {
            doReturn(Optional.of(expense))
                    .when(expenseRepository)
                    .findById(expense.getId());
            doReturn(expense)
                    .when(expenseRepository)
                    .save(expenseArgumentCaptor.capture());

            expenseService.update(expense.getId(), updateExpenseDTO);

            var expenseCaptured = expenseArgumentCaptor.getValue();

            assertEquals(updateExpenseDTO.expenseType(), expenseCaptured.getExpenseType());
            assertEquals(updateExpenseDTO.name(), expenseCaptured.getName());
            assertEquals(updateExpenseDTO.origin(), expenseCaptured.getOrigin());

            verify(expenseRepository, times(1)).findById(expense.getId());
            verify(expenseRepository, times(1)).save(expense);
        }

        @Test
        @DisplayName("Should throw exception when not finding the procedure")
        void ShouldThrowExceptionWhenNotFindingTheProcedure() {
            doReturn(Optional.empty())
                    .when(expenseRepository)
                    .findById(expense.getId());

            assertThrows(ExpenseNotFoundException.class,
                    () -> expenseService.update(expense.getId(), updateExpenseDTO));
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete the expense successfully")
        void MustDeleteTheExpenseSuccessfully() {
            doReturn(Optional.of(expense))
                    .when(expenseRepository)
                    .findById(expense.getId());
            doNothing()
                    .when(expenseRepository)
                    .deleteById(expense.getId());

            expenseService.deleteById(expense.getId());

            verify(expenseRepository, times(1)).findById(expense.getId());
            verify(expenseRepository, times(1)).deleteById(expense.getId());
        }

        @Test
        @DisplayName("Must throw an exception when do not find an expense")
        void MustThrowAnExceptionWhenDoNotFindAnExpense(){
            doReturn(Optional.empty())
                    .when(expenseRepository)
                    .findById(expense.getId());

            assertThrows(ExpenseNotFoundException.class,
                    () -> expenseService.update(expense.getId(), updateExpenseDTO));

            verify(expenseRepository, times(1))
                    .findById(expense.getId());
            verify(expenseRepository, times(0))
                    .deleteById(expense.getId());
        }
    }

    @Nested
    class findByNome {

        @Test
        @DisplayName("Must return expense successfully")
        void MustReturnExpenseSuccessfully() {
            doReturn(expense)
                    .when(expenseRepository)
                    .findByName(expense.getName());

            var response = expenseService.findByName(expense.getName());

            assertNotNull(response);
            assertEquals(response.getName(), expense.getName());
        }
        @Test
        @DisplayName("Must throw an exception when do not find an expense")
        void MustThrowAnExceptionWhenDoNotFindAnExpense(){
            doReturn(null)
                    .when(expenseRepository)
                    .findByName(expense.getName());

            assertThrows(ExpenseNotFoundException.class,
                    () -> expenseService.findByName(expense.getName()));
        }
    }
}