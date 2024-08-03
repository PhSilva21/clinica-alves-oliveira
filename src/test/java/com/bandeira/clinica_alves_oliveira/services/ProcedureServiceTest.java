package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.ProcedureRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateProceduresDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.ProcedureNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Procedure;
import com.bandeira.clinica_alves_oliveira.models.Specialty;
import com.bandeira.clinica_alves_oliveira.repositories.ProcedureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcedureServiceTest {

    @Mock
    ProcedureRepository procedureRepository;

    @InjectMocks
    ProcedureService procedureService;

    @Captor
    ArgumentCaptor<Procedure> procedureArgumentCaptor;

    Procedure procedure = new Procedure(
            "Canal",
            Specialty.ENDODONTIA,
            new BigDecimal("500.20"));

    ProcedureRequest procedureRequest = new ProcedureRequest(
            "Restauracao",
            Specialty.AESTHETICS,
            new BigDecimal("280.00")
    );

    UpdateProceduresDTO updateProcedureDTO = new UpdateProceduresDTO(
            "Extração",
            Specialty.DENTISTICS,
            new BigDecimal("140.00"));

    @Nested
    class createProcedure {

        @Test
        @DisplayName("Must create procedure successfully")
        void MustCreateProcedureSuccessfully() {
            doReturn(procedure)
                    .when(procedureRepository)
                    .save(procedureArgumentCaptor.capture());

            var response = procedureService.createProcedure(procedureRequest);

            var procedureCapture = procedureArgumentCaptor.getValue();

            assertEquals(procedureRequest.description(), procedureCapture.getDescription());
            assertEquals(procedureRequest.specialty(), procedureCapture.getSpecialty());
            assertEquals(procedureRequest.value(), procedureCapture.getValue());
        }
    }

    @Nested
    class findById {
        @Test
        @DisplayName("The procedure must be returned successfully")
        void TheProcedureMustBeReturnedSuccessfully() {
            doReturn(Optional.of(procedure))
                    .when(procedureRepository)
                    .findById(procedure.getId());

            var response = procedureService.findById(procedure.getId());

            assertTrue(response.isPresent());
            assertEquals(procedure.getId(), response.get().getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding procedure")
        void ShouldThrowExceptionWhenNotFindingProcedure() {
            doReturn(Optional.empty())
                    .when(procedureRepository)
                    .findById(procedure.getId());

            var response = procedureService.findById(procedure.getId());

            assertTrue(response.isEmpty());

            verify(procedureRepository, times(1))
                    .findById(procedure.getId());
        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("Must return procedures successfully")
        void MustReturnProceduresSuccessfully() {
            var procedureList = List.of(procedure);
            doReturn(procedureList)
                    .when(procedureRepository)
                    .findAll();

            var response = procedureService.findAll();

            assertNotNull(response);
            assertEquals(procedureList.size(), response.size());
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Must update procedure successfully")
        void MustUpdatePatientSuccessfully() {
            doReturn(Optional.of(procedure))
                    .when(procedureRepository)
                    .findById(procedure.getId());
            doReturn(procedure)
                    .when(procedureRepository)
                    .save(procedureArgumentCaptor.capture());

            procedureService.update(procedure.getId(), updateProcedureDTO);

            var procedureCapture = procedureArgumentCaptor.getValue();

            assertEquals(updateProcedureDTO.description(), procedureCapture.getDescription());
            assertEquals(updateProcedureDTO.specialty(), procedureCapture.getSpecialty());
            assertEquals(updateProcedureDTO.value(), procedureCapture.getValue());

            verify(procedureRepository, times(1)).findById(procedure.getId());
            verify(procedureRepository, times(1)).save(procedure);
        }

        @Test
        @DisplayName("Should throw exception when not finding procedure")
        void ShouldThrowExceptionWhenNotFindingProcedure() {
            doReturn(Optional.empty())
                    .when(procedureRepository)
                    .findById(procedure.getId());

            assertThrows(ProcedureNotFoundException.class,
                    () -> procedureService.update(procedure.getId(), updateProcedureDTO));
        }
    }

    @Nested
    class finsByName {
        @Test
        void findByNome() {
            doReturn(procedure)
                    .when(procedureRepository)
                    .findByDescription(procedure.getDescription());

            var response = procedureService.findByName(procedure.getDescription());

            assertNotNull(response);
            assertEquals(response.getDescription(), procedure.getDescription());
        }

        @Test
        @DisplayName("Must throw an exception when do not find an procedure")
        void MustThrowAnExceptionWhenDoNotFindAnProcedure() {
            doReturn(null)
                    .when(procedureRepository)
                    .findByDescription(procedure.getDescription());

            assertThrows(ProcedureNotFoundException.class,
                    () -> procedureService.findByName(procedure.getDescription()));
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete procedure successfully")
        void MustDeleteProcedureSuccessfully() {
            doReturn(Optional.of(procedure))
                    .when(procedureRepository)
                    .findById(procedure.getId());
            doNothing()
                    .when(procedureRepository)
                    .deleteById(procedure.getId());

            procedureService.deleteById(procedure.getId());

            verify(procedureRepository, times(1)).findById(procedure.getId());
            verify(procedureRepository, times(1)).deleteById(procedure.getId());
        }


        @Test
        @DisplayName("Should throw exception when not finding procedure")
        void ShouldThrowExceptionWhenNotFindingProcedure() {
            doReturn(Optional.empty())
                    .when(procedureRepository)
                    .findById(procedure.getId());

            assertThrows(ProcedureNotFoundException.class,
                    () -> procedureService.update(procedure.getId(), updateProcedureDTO));

            verify(procedureRepository, times(1))
                    .findById(procedure.getId());
            verify(procedureRepository, times(0))
                    .deleteById(procedure.getId());
        }
    }
}
