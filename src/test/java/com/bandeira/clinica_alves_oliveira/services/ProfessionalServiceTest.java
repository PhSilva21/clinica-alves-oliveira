package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.ProfessionalRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateProfessionalDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.*;
import com.bandeira.clinica_alves_oliveira.repositories.ProfessionalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessionalServiceTest {

    @InjectMocks
    ProfessionalService professionalService;

    @Mock
    ProfessionalRepository professionalRepository;

    @Mock
    ViaCepService viaCepService;

    @Captor
    ArgumentCaptor<Professional> professionalArgumentCaptor;

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

    UpdateProfessionalDTO updateProfessionalDTO = new UpdateProfessionalDTO(
            "Carla",
            LocalDate.of(2000, 12,13),
            "198921",
            "2121721",
            "Cirurgião Dentista",
            "1731831",
            "16A",
            "Bloco C",
            "ausyasagsyga",
            12328127,
            127172,
            "auhsuhaus",
            "17177",
            "731771");
    ;

    ProfessionalRequest professionalRequest = new ProfessionalRequest(
            "Julia",
            LocalDate.of(2017,11,21),
            "198921",
            "2121721",
            "Recepcionista",
            "131313",
            "23",
            "asasas",
            "pedro@gmail.com",
            1192227818,
            232377331,
            "ausyasagsyga",
            "1199999132",
            "127172"
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

    Address address = new Address(
            "21212121",
            "Rua frizado",
            "Bloco A",
            "Parque jacoimbra",
            "Sao Paulo",
            "SP",
            "sdadaa",
            "dadadada",
            "011",
            "3dwdwd"
    );

    @Nested
    class CreateProfessional {

        @Test
        @DisplayName("Must create the Professional successfully")
        void register() throws IOException {
            doReturn(professional)
                    .when(professionalRepository)
                    .save(professionalArgumentCaptor.capture());
            doReturn(address)
                    .when(viaCepService)
                    .getAddress(professionalRequest.cep());

            var response = professionalService.createProfessional(professionalRequest);

            assertNotNull(response);

            var professionalCaptured = professionalArgumentCaptor.getValue();

            assertEquals(professionalRequest.name(), professionalCaptured.getName());
            assertEquals(professionalRequest.dateOfBirth(), professionalCaptured.getDateOfBirth());
            assertEquals(professionalRequest.rg(), professionalCaptured.getRg());
            assertEquals(professionalRequest.cpf(), professionalCaptured.getCpf());
            assertEquals(professionalRequest.profession(), professionalCaptured.getProfession());
            assertEquals(professionalRequest.cep(), professionalCaptured.getCep());
            assertEquals(address.getUf(), professionalCaptured.getState());
            assertEquals(address.getLocalidade(), professionalCaptured.getCity());
            assertEquals(address.getLogradouro(), professionalCaptured.getStreet());
            assertEquals(professionalRequest.number(), professionalCaptured.getNumber());
            assertEquals(address.getBairro(), professionalCaptured.getNeighborhood());
            assertEquals(professionalRequest.complement(), professionalCaptured.getComplement());
            assertEquals(professionalRequest.email(), professionalCaptured.getEmail());
            assertEquals(professionalRequest.cel(), professionalCaptured.getCel());
            assertEquals(professionalRequest.tel(), professionalCaptured.getTel());
            assertEquals(professionalRequest.bank(), professionalCaptured.getBank());
            assertEquals(professionalRequest.agency(), professionalCaptured.getAgency());

        }
    }

    @Nested
    class queryList {

        @Test
        @DisplayName("Must return queries successfully")
        void MustReturnQueriesSuccessfully() {
            var queryList = List.of(query);
            doReturn(professional)
                    .when(professionalRepository)
                    .findByName(professional.getName());

            professional.getQueries().add(query);

            var response = professionalService.queryLIst(professional.getName());

            assertNotNull(response);
            assertEquals(queryList.size(), response.size());
        }
        @Test
        @DisplayName("Should make an exception if don't find a professional")
        void ShouldMakeAnExceptionIfDontFindAProfessional(){
            doReturn(null)
                    .when(professionalRepository)
                    .findByName(professional.getName());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> professionalService.queryLIst(professional.getName()));
        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("Must return professionals successfully")
        void mustReturnProfessionalsSuccessfully() {
            var professionalList = List.of(professional);
            doReturn(List.of(professional))
                    .when(professionalRepository)
                    .findAll();

            var response = professionalService.findAll();

            assertNotNull(response);
            assertEquals(response.size(), professionalList.size());
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Must return professional successfully")
        void MustReturnProfessionalSuccessfully() {
            doReturn(professional)
                    .when(professionalRepository)
                    .findByName(professionalRequest.name());

            var response = professionalService.findByName(professionalRequest.name());

            assertNotNull(response);
            assertEquals(professional.getName(), response.getName());
        }
        @DisplayName("Should throw exception when not finding professional")
        void ShouldThrowExceptionWhenNotFindingProfessional() {
            doReturn(Optional.empty())
                    .when(professionalRepository)
                    .findById(professional.getId());

            var response = professionalService.findByName(professional.getName());

          assertThrows(ProfessionalNotFoundException.class,
                  () -> professionalService.findByName(professional.getName()));
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Must update professionally successfully")
        void MustUpdateProfessionallySuccessfully() throws IOException {
            doReturn(Optional.of(professional))
                    .when(professionalRepository)
                    .findById(professional.getId());
            doReturn(address)
                    .when(viaCepService)
                    .getAddress(updateProfessionalDTO.cep());
            doReturn(professional)
                    .when(professionalRepository)
                    .save(professionalArgumentCaptor.capture());

            professionalService.update(professional.getId(), updateProfessionalDTO);

            var professionalCaptured = professionalArgumentCaptor.getValue();

            assertEquals(updateProfessionalDTO.name(), professionalCaptured.getName());
            assertEquals(updateProfessionalDTO.dateOfBirth(), professionalCaptured.getDateOfBirth());
            assertEquals(updateProfessionalDTO.rg(), professionalCaptured.getRg());
            assertEquals(updateProfessionalDTO.cpf(), professionalCaptured.getCpf());
            assertEquals(updateProfessionalDTO.profession(), professionalCaptured.getProfession());
            assertEquals(updateProfessionalDTO.cep(), professionalCaptured.getCep());
            assertEquals(address.getUf(), professionalCaptured.getState());
            assertEquals(address.getLocalidade(), professionalCaptured.getCity());
            assertEquals(address.getLogradouro(), professionalCaptured.getStreet());
            assertEquals(updateProfessionalDTO.number(), professionalCaptured.getNumber());
            assertEquals(address.getBairro(), professionalCaptured.getNeighborhood());
            assertEquals(updateProfessionalDTO.complement(), professionalCaptured.getComplement());
            assertEquals(updateProfessionalDTO.email(), professionalCaptured.getEmail());
            assertEquals(updateProfessionalDTO.cel(), professionalCaptured.getCel());
            assertEquals(updateProfessionalDTO.tel(), professionalCaptured.getTel());
            assertEquals(updateProfessionalDTO.bank(), professionalCaptured.getBank());
            assertEquals(updateProfessionalDTO.agency(), professionalCaptured.getAgency());

            verify(professionalRepository, times(1))
                    .findById(professional.getId());
            verify(viaCepService, times(1))
                    .getAddress(updateProfessionalDTO.cep());
            verify(professionalRepository, times(1)).save(professional);
        }
        @Test
        @DisplayName("Should throw exception when not finding professional")
        void ShouldThrowExceptionWhenNotFindingProfessional() {
            doReturn(Optional.empty())
                    .when(professionalRepository)
                    .findById(professional.getId());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> professionalService.update(professional.getId(), updateProfessionalDTO));

            verify(professionalRepository, times(1))
                    .findById(professional.getId());
            verify(professionalRepository, times(0))
                    .save(professional);
        }
    }
    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete professional successfully")
        void MustDeleteProfessionalSuccessfully(){
            doReturn(Optional.of(professional))
                    .when(professionalRepository)
                    .findById(professional.getId());
            doNothing()
                    .when(professionalRepository)
                    .deleteById(professional.getId());

            professionalService.deleteById(professional.getId());

            verify(professionalRepository, times(1))
                    .findById(professional.getId());
            verify(professionalRepository, times(1))
                    .deleteById(professional.getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding professional")
        void ShouldThrowExceptionWhenNotFindingProfessional() {
            doReturn(Optional.empty())
                    .when(professionalRepository)
                    .findById(professional.getId());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> professionalService.deleteById(professional.getId()));

            verify(professionalRepository, times(1))
                    .findById(professional.getId());
            verify(professionalRepository, times(0))
                    .deleteById(professional.getId());
        }
    }
}