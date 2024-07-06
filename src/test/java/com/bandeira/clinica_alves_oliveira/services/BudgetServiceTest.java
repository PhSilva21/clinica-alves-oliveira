package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.BudgetRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateBudgetDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.BudgetNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProcedureNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.*;
import com.bandeira.clinica_alves_oliveira.repositories.BudgetRepository;
import com.bandeira.clinica_alves_oliveira.repositories.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @InjectMocks
    BudgetService budgetService;

    @Mock
    BudgetRepository budgetRepository;

    @Mock
    PatientService patientService;

    @Mock
    ProfessionalService professionalService;

    @Mock
    ProcedureService procedureService;

    @Captor
    ArgumentCaptor<Budget> budgetArgumentCaptor;

    @Mock
    PatientRepository patientRepository;

    Patient patient = new Patient(
            "Paula",
            "198921",
            "2121721",
            LocalDate.of(2017,11,21),
            "1731831",
            "Sao Palu",
            "Sao Pailo",
            "asasas",
            "112b",
            "Vila jacui",
            "aui",
            "ausyasagsyga",
            12328127,
            127172,
            "auhsuhaus",
            "17177");

    Professional professional = new Professional(
            "Carla",
            LocalDate.of(2000, 12,13),
            "198921",
            "2121721",
            "Cirurgião Dentista",
            "1731831",
            "Sao Palu",
            "Sao Pailo",
            "asasas",
            "112b",
            "Vila jacui",
            "Bloco C",
            "ausyasagsyga",
            12328127,
            127172,
            "auhsuhaus",
            "17177",
            "731771");

    Procedure procedure = new Procedure(
            "Canal",
            Specialty.AESTHETICS,
            133.00);

    Budget budget = new Budget(
            LocalDate.now(),
            StatusBudget.CONTRACTED,
            "Flavia",
            "Caio",
            "Profilaxia",
            patient,
            professional,
            procedure.getValue(),
            procedure
    );

    BudgetRequest budgetRequest = new BudgetRequest(
            LocalDate.of(2024, 11, 16),
            StatusBudget.NOT_CONTRACTED,
            "Pedro",
            "Sofia",
            "Restauração");

    UpdateBudgetDTO updateBudgetDTO = new UpdateBudgetDTO(
            LocalDate.of(2024, 11, 16),
            StatusBudget.NOT_CONTRACTED,
            "Pedro",
            "Sofia",
            "Restauracao");

    @Nested
    class createBudget {

        @Test
        @DisplayName("Must create budget successfully")
        void MustCreateBudgetSuccessfully() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(budgetRequest.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(budgetRequest.nameProfessional());
            doReturn(procedure).when(procedureService)
                    .findByName(budgetRequest.nameProcedure());
            doReturn(budget)
                    .when(budgetRepository)
                    .save(budgetArgumentCaptor.capture());


            var response = budgetService.createBudget(budgetRequest);

            var budgetCaptured = budgetArgumentCaptor.getValue();


            assertEquals(budgetRequest.dateRegister(), budgetCaptured.getDateRegister());
            assertEquals(budgetRequest.statusBudget(), budgetCaptured.getStatusBudget());
            assertEquals(budgetRequest.namePatient(), budgetCaptured.getNamePatient());
            assertEquals(budgetRequest.nameProfessional(), budgetCaptured.getNameProfessional());
            assertEquals(budgetRequest.nameProcedure(), budgetCaptured.getNameProcedure());
            assertEquals(patient, budgetCaptured.getPatient());
            assertEquals(professional, budgetCaptured.getProfessional());
            assertEquals(procedure, budgetCaptured.getProcedure());
            assertEquals(procedure.getValue(), budgetCaptured.getValue());
        }

        @Test
        @DisplayName("Should throw exception when not finding the patient")
        void ShouldThrowExceptionWhenNotFindingThePatient() {
            doReturn(null)
                    .when(patientService)
                    .findByName(budgetRequest.namePatient());

            assertThrows(PatientNotFoundException.class, () -> budgetService.createBudget(budgetRequest));
        }


        @Test
        @DisplayName("Should throw exception when not finding the professional")
        void ShouldThrowExceptionWhenNotFindingTheProfessional() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(budgetRequest.namePatient());


            assertThrows(ProfessionalNotFoundException.class,
                    () -> budgetService.createBudget(budgetRequest));
        }

        @Test
        void erroProcedureBudgetCreate() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(budgetRequest.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(budgetRequest.nameProfessional());

            assertThrows(ProcedureNotFoundException.class, () -> budgetService.createBudget(budgetRequest));
        }
    }

    @Nested
    class findAll {

        @Test
                @DisplayName("Must return all budgets")
        void MustReturnAllBudgets() {
            var budgetList = List.of(budget);

            doReturn(budgetList)
                    .when(budgetRepository)
                    .findAll();

            var response = budgetService.findAll();

            assertNotNull(response);
            assertEquals(budgetList.size(), response.size());
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Must return the budget successfully")
        void MustReturnTheBudgetSuccessfully() {
            doReturn(Optional.of(budget))
                    .when(budgetRepository)
                    .findById(budget.getId());

            var response = budgetService.findById(budget.getId());

            assertTrue(response.isPresent());
            assertEquals(budget.getId(), response.get().getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding budget")
        void ShouldThrowExceptionWhenNotFindingBudget(){
            doReturn(Optional.empty())
                    .when(budgetRepository)
                    .findById(budget.getId());

            var response = budgetService.findById(budget.getId());

            assertTrue(response.isEmpty());

            verify(budgetRepository, times(1))
                    .findById(budget.getId());
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Must update budget successfully")
        void MustUpdateBudgetSuccessfully() {
            doReturn(Optional.of(budget))
                    .when(budgetRepository)
                    .findById(budget.getId());
            doReturn(patient)
                    .when(patientService)
                    .findByName(updateBudgetDTO.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(updateBudgetDTO.nameProfessional());
            doReturn(procedure)
                    .when(procedureService)
                    .findByName(updateBudgetDTO.nameProcedure());
            doReturn(patient).
                    when(patientRepository).
                    save(patient);
            doReturn(budget)
                    .when(budgetRepository)
                    .save(budgetArgumentCaptor.capture());

            budgetService.update(budget.getId(), updateBudgetDTO);

            var budgetCaptured = budgetArgumentCaptor.getValue();

            assertEquals(budget.getId(), budgetCaptured.getId());
            assertEquals(updateBudgetDTO.dateRegister(), budgetCaptured.getDateRegister());
            assertEquals(updateBudgetDTO.statusBudget(), budgetCaptured.getStatusBudget());
            assertEquals(updateBudgetDTO.namePatient(), budgetCaptured.getNamePatient());
            assertEquals(updateBudgetDTO.nameProfessional(), budgetCaptured.getNameProfessional());
            assertEquals(patient, budgetCaptured.getPatient());
            assertEquals(professional, budgetCaptured.getProfessional());
            assertEquals(procedure, budgetCaptured.getProcedure());
            assertEquals(procedure.getValue(), budgetCaptured.getValue());

            verify(budgetRepository, times(1))
                    .findById(budget.getId());
            verify(patientService, times(1))
                    .findByName(updateBudgetDTO.namePatient());
            verify(professionalService, times(1))
                    .findByName(updateBudgetDTO.nameProfessional());
            verify(procedureService, times(1))
                    .findByName(updateBudgetDTO.nameProcedure());
            verify(budgetRepository, times(1))
                    .save(budget);
        }

        @Test
        @DisplayName("Should throw exception when not finding the budget")
        void ShouldThrowExceptionWhenNotFindingBudget() {
            doReturn(Optional.empty())
                    .when(budgetRepository).findById(budget.getId());

            assertThrows(BudgetNotFoundException.class,
                    () -> budgetService.update(budget.getId(), updateBudgetDTO));
        }

        @Test
        @DisplayName("Must throw exception when not finding the patient")
        void MustThrowExceptionWhenNotFindingThePatient() {
            doReturn(Optional.of(budget))
                    .when(budgetRepository)
                    .findById(budget.getId());

            assertThrows(PatientNotFoundException.class,
                    () -> budgetService.update(budget.getId(), updateBudgetDTO));
        }

        @Test
        @DisplayName("Must throw exception when not finding the professional")
        void MustThrowExceptionWhenNotFindingTheProfessional() {
            doReturn(Optional.of(budget))
                    .when(budgetRepository)
                    .findById(budget.getId());
            doReturn(patient)
                    .when(patientService)
                    .findByName(updateBudgetDTO.namePatient());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> budgetService.update(budget.getId(), updateBudgetDTO));
        }

        @Test
        @DisplayName("Should throw exception when not finding the procedure")
        void MustThrowExceptionWhenNotFindingTheProcedure() {
            doReturn(Optional.of(budget))
                    .when(budgetRepository)
                    .findById(budget.getId());
            doReturn(patient)
                    .when(patientService)
                    .findByName(updateBudgetDTO.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(updateBudgetDTO.nameProfessional());

            assertThrows(ProcedureNotFoundException.class,
                    () -> budgetService.update(budget.getId(), updateBudgetDTO));
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete budget successfully")
        void MustDeleteBudgetSuccessfully() {
            doReturn(Optional.of(budget))
                    .when(budgetRepository)
                    .findById(budget.getId());
            doNothing()
                    .when(budgetRepository)
                    .deleteById(budget.getId());

            budgetService.deleteById(patient.getId());

            verify(budgetRepository, times(1)).findById(budget.getId());
            verify(budgetRepository, times(1)).deleteById(budget.getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding the budget")
        void ShouldThrowExceptionWhenNotFindingBudget() {
            doReturn(Optional.empty())
                    .when(budgetRepository)
                    .findById(budget.getId());


            assertThrows(BudgetNotFoundException.class, () -> budgetService.deleteById(patient.getId()));

            verify(budgetRepository, times(1)).findById(budget.getId());
            verify(budgetRepository, times(0)).deleteById(budget.getId());
        }
    }
}