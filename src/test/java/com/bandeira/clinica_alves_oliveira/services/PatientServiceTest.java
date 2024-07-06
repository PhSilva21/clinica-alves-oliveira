package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.PatientRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePatientDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Address;
import com.bandeira.clinica_alves_oliveira.models.Patient;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Mock
    private ViaCepService viaCepService;

    @Captor
    private ArgumentCaptor<Patient> patientArgumentCaptor;

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

    UpdatePatientDTO updatePatientDTO = new UpdatePatientDTO(
            "Paula",
            "198921",
            "2121721",
            LocalDate.of(2017,11,21),
            "1731831",
            "Vila jacui",
            "aui",
            "ausyasagsyga",
            "127172",
            "auhsuhaus",
            "17177",
            "qys7qysqy7sqqq",
            119226362,
            129182122,
            "61261625121",
            "182712121",
            64.00,
            64.00,
            0.00
    );

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
            "17177"
    );
    PatientRequest patientRequest = new PatientRequest(
            "Paula",
            "198921",
            "2121721",
            LocalDate.now(),
            "1731831",
            "112b",
            "aui",
            "ausyasagsyga",
            12121813,
            12328127,
            "Helio",
            "auhsuhaus"
    );

    @Nested
    class createPatient {

        @Test
        @DisplayName("Must create patient successfully")
        void mustCreatePatientSuccessfully() throws IOException {
            doReturn(patient)
                    .when(patientRepository)
                    .save(patientArgumentCaptor.capture());
            doReturn(address)
                    .when(viaCepService)
                    .getAddress(patientRequest.cep());

            var response = patientService.createPatient(patientRequest);

            assertNotNull(response);

            var patientCaptured = patientArgumentCaptor.getValue();

            assertEquals(patientRequest.name(), patientCaptured.getName());
            assertEquals(patientRequest.cpf(), patientCaptured.getCpf());
            assertEquals(patientRequest.rg(), patientCaptured.getRg());
            assertEquals(patientRequest.cep(), patientCaptured.getCep());
            assertEquals(address.getUf(), patientCaptured.getState());
            assertEquals(address.getLocalidade(), patientCaptured.getCity());
            assertEquals(address.getLogradouro(), patientCaptured.getStreet());
            assertEquals(patientRequest.number(), patientCaptured.getNumber());
            assertEquals(address.getBairro(), patientCaptured.getNeighborhood());
            assertEquals(patientRequest.complement(), patientCaptured.getComplement());
            assertEquals(patientRequest.email(), patientCaptured.getEmail());
            assertEquals(patientRequest.cel(), patientCaptured.getCel());
            assertEquals(patientRequest.tel(), patientCaptured.getTel());
            assertEquals(patientRequest.responsible(), patientCaptured.getResponsible());
            assertEquals(patientRequest.cpfOfResponsible(), patientCaptured.getResponsible());
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Must return patient successfully")
        void MustReturnPatientSuccessfully() {
            doReturn(Optional.of(patient))
                    .when(patientRepository)
                    .findById(patient.getId());

            var response = patientService.findById(patient.getId());

            assertTrue(response.isPresent());
            assertEquals(patient.getId(), response.get().getId());
        }

        @Test
        @DisplayName("Should throw exception when not finding patient")
        void ShouldThrowExceptionWhenNotFindingPatient() {
            doReturn(Optional.empty())
                    .when(patientRepository)
                    .findById(patient.getId());

            var response = patientService.findById(patient.getId());

            assertTrue(response.isEmpty());

            verify(patientRepository, times(1))
                    .findById(patient.getId());
        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("Must return patients successfully")
        void MustReturnPatientsSuccessfully() {
            var patientList = List.of(patient);
            doReturn(patientList)
                    .when(patientRepository)
                    .findAll();

            var response = patientService.findAll();

            assertNotNull(response);
            assertEquals(patientList.size(), response.size());
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Must update patient successfully")
        void MustUpdatePatientSuccessfully() throws IOException {
            doReturn(Optional.of(patient))
                    .when(patientRepository)
                    .findById(patient.getId());
            doReturn(address)
                    .when(viaCepService)
                    .getAddress(updatePatientDTO.cep());
            doReturn(patient)
                    .when(patientRepository)
                    .save(patientArgumentCaptor.capture());

            patientService.update(patient.getId(), updatePatientDTO);

            var patientCaptured = patientArgumentCaptor.getValue();

            assertEquals(updatePatientDTO.name(), patientCaptured.getName());
            assertEquals(updatePatientDTO.cpf(), patientCaptured.getCpf());
            assertEquals(updatePatientDTO.rg(), patientCaptured.getRg());
            assertEquals(updatePatientDTO.cep(), patientCaptured.getCep());
            assertEquals(address.getUf(), patientCaptured.getState());
            assertEquals(address.getLocalidade(), patientCaptured.getCity());
            assertEquals(address.getLogradouro(), patientCaptured.getStreet());
            assertEquals(updatePatientDTO.number(), patientCaptured.getNumber());
            assertEquals(address.getBairro(), patientCaptured.getNeighborhood());
            assertEquals(updatePatientDTO.complement(), patientCaptured.getComplement());
            assertEquals(updatePatientDTO.email(), patientCaptured.getEmail());
            assertEquals(updatePatientDTO.cel(), patientCaptured.getCel());
            assertEquals(updatePatientDTO.tel(), patientCaptured.getTel());
            assertEquals(updatePatientDTO.responsible(), patientCaptured.getResponsible());
            assertEquals(updatePatientDTO.cpfOfResponsible(), patientCaptured.getResponsible());

            verify(patientRepository, times(1))
                    .findById(patient.getId());
            verify(patientRepository, times(1))
                    .save(patient);
            verify(viaCepService, times(1))
                    .getAddress(updatePatientDTO.cep());
        }

        @Test
        @DisplayName("Must throw exception when not finding patient")
        void MustThrowExceptionWhenNotFindingPatient() throws IOException {
            doReturn(Optional.empty())
                    .when(patientRepository)
                    .findById(patient.getId());

            assertThrows(PatientNotFoundException.class,
                    () -> patientService.update(patient.getId(), updatePatientDTO));

            verify(patientRepository, times(1))
                    .findById(patient.getId());
            verify(patientRepository, times(0))
                    .save(patient);
            verify(viaCepService, times(0))
                    .getAddress(updatePatientDTO.cep());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete patient successfully")
        void MustDeletePatientSuccessfully() {
            doReturn(Optional.of(patient))
                    .when(patientRepository)
                    .findById(patient.getId());
            doNothing()
                    .when(patientRepository)
                    .deleteById(patient.getId());

            patientService.deleteById(patient.getId());

            verify(patientRepository, times(1))
                    .findById(patient.getId());
            verify(patientRepository, times(1))
                    .deleteById(patient.getId());
        }

        @Test
        @DisplayName("Must throw exception when not finding patient")
        void MustThrowExceptionWhenNotFindingPatient() {
            doReturn(Optional.empty())
                    .when(patientRepository)
                    .findById(patient.getId());

            assertThrows(PatientNotFoundException.class,
                    () -> patientService.update(patient.getId(), updatePatientDTO));

            verify(patientRepository, times(1))
                    .findById(patient.getId());
            verify(patientRepository, times(0))
                    .deleteById(patient.getId());
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Must return patient successfully")
        void MustReturnPatientSuccessfully() {
            doReturn(patient)
                    .when(patientRepository)
                    .findByName(patient.getName());

            var response = patientService.findByName(patient.getName());

            assertNotNull(response);
            assertEquals(response.getName(), patient.getName());
        }

        @Test
        @DisplayName("Must throw an exception when do not find an patient")
        void MustThrowAnExceptionWhenDoNotFindAnPatient() {
            doReturn(null)
                    .when(patientRepository)
                    .findByName(patient.getName());

            assertThrows(PatientNotFoundException.class,
                    () -> patientService.findByName(patient.getName()));
        }
    }

        @Nested
        class birthdays {

            @Test
            @DisplayName("Must return birthdays")
            void MustReturnBirthdays() {
                var patientList = List.of(patient);

                doReturn(patientList)
                        .when(patientRepository)
                        .findAll();

                List<Patient> aniversarianteList = patientList.stream().filter(p -> p.getDateOfBirth()
                                .equals(LocalDate.of(2017, 03, 21)))
                        .toList();

                var response = patientService.birthdays();

                assertNotNull(response);
                assertEquals(aniversarianteList.size(), response.size());
            }
        }
}