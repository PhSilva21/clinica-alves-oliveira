package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.QueryRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateQueryDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.QueryNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Patient;
import com.bandeira.clinica_alves_oliveira.models.Professional;
import com.bandeira.clinica_alves_oliveira.models.Query;
import com.bandeira.clinica_alves_oliveira.models.StatusQuery;
import com.bandeira.clinica_alves_oliveira.repositories.QueryRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {

    @InjectMocks
    QueryService queryService;

    @Mock
    QueryRepository queryRepository;

    @Mock
    PatientService patientService;

    @Mock
    ProfessionalService professionalService;

    @Captor
    ArgumentCaptor<Query> queryArgumentCaptor;

    QueryRequest queryRequest = new QueryRequest(
            "Julia",
            "Carla",
            LocalDateTime.of(2024,03,12, 17, 31, 17 , 023),
            60,
            StatusQuery.SCHEDULED
    );

    UpdateQueryDTO updateQueryDTO = new UpdateQueryDTO(
            "Julia",
            "Caio",
            LocalDateTime.of(2024,11, 17,17,30 ),
            60,
            StatusQuery.SCHEDULED
    );

    Patient patient = new Patient(
            "Julia",
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
            1199999132,
            127172,
            "auhsuhaus",
            "17177",
            new BigDecimal("0.00"),
            new BigDecimal("0.00"),
            new BigDecimal("0.00")
    );

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

    Query query = new Query(
            "Julia",
            "Carla",
            patient,
            professional,
            LocalDateTime.of(2024, 11, 14, 15, 16,07),
            60,
            StatusQuery.SCHEDULED,
            1199999132
    );

    @Nested
    class createQuery {

        @Test
        void create() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(queryRequest.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(queryRequest.nameProfessional());
            doReturn(query)
                    .when(queryRepository)
                    .save(queryArgumentCaptor.capture());

            var response = queryService.createQuery(queryRequest);

            assertNotNull(response);

            var queryCaptured = queryArgumentCaptor.getValue();

            assertEquals(queryRequest.namePatient(), queryCaptured.getNamePatient());
            assertEquals(queryRequest.nameProfessional(), queryCaptured.getNameProfessional());
            assertEquals(patient, queryCaptured.getPatient());
            assertEquals(professional, queryCaptured.getProfessional());
            assertEquals(queryRequest.time(), queryCaptured.getTime());
            assertEquals(queryRequest.duration(), queryCaptured.getDuration());
            assertEquals(queryRequest.statusQuery(), queryCaptured.getStatusQuery());
            assertEquals(patient.getCel(), queryCaptured.getPatientCel());
        }
        @Test
        @DisplayName("Should throw exception when not finding patient")
        void ShouldThrowExceptionWhenNotFindingPatient(){
            doReturn(null)
                    .when(patientService)
                    .findByName(queryRequest.namePatient());

            assertThrows(PatientNotFoundException.class,
                    () -> queryService.createQuery(queryRequest));
        }
        @Test
        @DisplayName("Should throw exception when not finding professional")
        void ShouldThrowExceptionWhenNotFindingProfessional() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(queryRequest.namePatient());
            doReturn(null)
                    .when(professionalService)
                    .findByName(queryRequest.nameProfessional());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> queryService.createQuery(queryRequest));

        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("Must return queries successfully")
        void MustReturnQueriesSuccessfully() {
            var queryList = List.of(query);

            Sort sort = Sort.by("time").ascending();

            doReturn(queryList)
                    .when(queryRepository)
                    .findAll(sort);

            var response = queryService.findAll();

            assertNotNull(response);
            assertEquals(queryList.size(), response.size());
        }
    }
    @Nested
    class update {

        @Test
        @DisplayName("Should update query successfully")
        void ShouldUpdateQuerySuccessfully() {
            doReturn(Optional.of(query))
                    .when(queryRepository)
                    .findById(query.getId());
            doReturn(patient)
                    .when(patientService)
                    .findByName(updateQueryDTO.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(updateQueryDTO.nameProfessional());
            doReturn(query)
                    .when(queryRepository)
                    .save(queryArgumentCaptor.capture());

            queryService.update(query.getId(), updateQueryDTO);

            var queryCaptured = queryArgumentCaptor.getValue();

            assertEquals(query.getId(), queryCaptured.getId());
            assertEquals(updateQueryDTO.namePatient(), queryCaptured.getNamePatient());
            assertEquals(updateQueryDTO.nameProfessional(), queryCaptured.getNameProfessional());
            assertEquals(patient, queryCaptured.getPatient());
            assertEquals(professional, queryCaptured.getProfessional());
            assertEquals(updateQueryDTO.time(), queryCaptured.getTime());
            assertEquals(updateQueryDTO.duration(), queryCaptured.getDuration());
            assertEquals(updateQueryDTO.statusQuery(), queryCaptured.getStatusQuery());
            assertEquals(patient.getCel(), queryCaptured.getPatientCel());

            verify(queryRepository, times(1))
                    .findById(query.getId());
            verify(patientService, times(1))
                    .findByName(updateQueryDTO.namePatient());
            verify(professionalService, times(1))
                    .findByName(updateQueryDTO.nameProfessional());
            verify(queryRepository, times(1)).save(query);
        }

        @Test
        @DisplayName("Should throw exception when not finding query")
        void ShouldThrowExceptionWhenNotFindingQuery() {
            doReturn(Optional.empty())
                    .when(queryRepository)
                    .findById(query.getId());

            assertThrows(QueryNotFoundException.class,
                    () -> queryService.update(query.getId(), updateQueryDTO));

            verify(queryRepository, times(1))
                    .findById(query.getId());
            verify(patientService, times(0))
                    .findByName(updateQueryDTO.namePatient());
            verify(professionalService, times(0))
                    .findByName(updateQueryDTO.nameProfessional());
            verify(queryRepository, times(0)).save(query);
        }

        @DisplayName("Should throw exception when not finding patient")
        void ShouldThrowExceptionWhenNotFindingPatient() {
            doReturn(Optional.of(query))
                    .when(queryRepository)
                    .findById(query.getId());
            doReturn(null)
                    .when(patientService)
                    .findByName(queryRequest.namePatient());

            assertThrows(PatientNotFoundException.class,
                    () -> queryService.update(query.getId(), updateQueryDTO));

            verify(queryRepository, times(1))
                    .findById(query.getId());
            verify(patientService, times(1))
                    .findByName(updateQueryDTO.namePatient());
            verify(professionalService, times(0))
                    .findByName(updateQueryDTO.nameProfessional());
            verify(queryRepository, times(0)).save(query);
        }

        @Test
        @DisplayName("Should throw exception when not finding professional")
        void ShouldThrowExceptionWhenNotFindingProfessional() {
            doReturn(Optional.of(query))
                    .when(queryRepository)
                    .findById(query.getId());
            doReturn(patient)
                    .when(patientService)
                    .findByName(updateQueryDTO.namePatient());
            doReturn(null)
                    .when(professionalService)
                    .findByName(updateQueryDTO.nameProfessional());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> queryService.update(query.getId(), updateQueryDTO));
            verify(queryRepository, times(1))
                    .findById(query.getId());
            verify(patientService, times(1))
                    .findByName(updateQueryDTO.namePatient());
            verify(professionalService, times(1))
                    .findByName(updateQueryDTO.nameProfessional());
            verify(queryRepository, times(0)).save(query);
        }

    }
    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete query successfully")
        void MustDeleteQuerySuccessfully() {
            doReturn(Optional.of(query))
                    .when(queryRepository)
                    .findById(query.getId());
            doNothing()
                    .when(queryRepository)
                    .deleteById(query.getId());

            queryService.deleteById(query.getId());

            verify(queryRepository, times(1)).findById(query.getId());
            verify(queryRepository, times(1)).deleteById(query.getId());
        }
        @Test
        @DisplayName("Should throw exception when not finding query")
        void ShouldThrowExceptionWhenNotFindingQuery() {
            doReturn(Optional.empty())
                    .when(queryRepository)
                    .findById(query.getId());

            assertThrows(QueryNotFoundException.class,
                    () -> queryService.deleteById(query.getId()));
        }
    }

    @Nested
    class findBtId {

        @Test
        @DisplayName("Must return query successfully")
        void MustReturnQuerySuccessfully() {
            doReturn(Optional.of(query))
                    .when(queryRepository)
                    .findById(query.getId());

            var response = queryService.findById(query.getId());

            assertTrue(response.isPresent());
            assertEquals(query.getId(), response.get().getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding query")
        void ShouldThrowExceptionWhenNotFindingQuery() {
            doReturn(Optional.empty())
                    .when(queryRepository)
                    .findById(query.getId());

            var response = queryService.findById(query.getId());

            assertTrue(response.isEmpty());
        }
    }
}