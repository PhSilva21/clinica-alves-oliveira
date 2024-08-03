package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.PaymentRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePaymentDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.OutstandingBalanceException;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.PaymentNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.FormOfPayment;
import com.bandeira.clinica_alves_oliveira.models.Patient;
import com.bandeira.clinica_alves_oliveira.models.Payment;
import com.bandeira.clinica_alves_oliveira.models.Professional;
import com.bandeira.clinica_alves_oliveira.repositories.PatientRepository;
import com.bandeira.clinica_alves_oliveira.repositories.PaymentRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PatientRepository patientRepository;

    @Mock
    ProfessionalService professionalService;

    @Mock
    PatientService patientService;

    @Captor
    ArgumentCaptor<Payment> paymentArgumentCaptor;

    PaymentRequest paymentRequest = new PaymentRequest(
            new BigDecimal("160.00"),
            "Julia",
            LocalDateTime.of(2024,04, 13, 15, 35, 16),
            FormOfPayment.PIX,
            "Caio"
    );

    UpdatePaymentDTO updatePaymentDTO = new UpdatePaymentDTO(
            new BigDecimal("120.00"),
            "Lucas",
            LocalDateTime.now(),
            FormOfPayment.MONEY,
            "Caio"
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
            new BigDecimal("0.00"));

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



    Payment payment = new Payment(
            new BigDecimal("400.00"),
            "Flavio",
            patient,
            LocalDateTime.now(),
            FormOfPayment.MONEY,
            "Patricia",
            professional
    );

    @Nested
    class createPayment {

        @Test
        @DisplayName("Must create payment successfully")
        void MustCreatePaymentSuccessfully() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(paymentRequest.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(paymentRequest.nameProfessional());
            doReturn(patient)
                    .when(patientRepository)
                    .save(patient);
            doReturn(payment)
                    .when(paymentRepository)
                    .save(paymentArgumentCaptor.capture());

            patient.setOutstandingBalance(new BigDecimal("329.00"));

            var response = paymentService.createPayment(paymentRequest);

            var paymentCaptured = paymentArgumentCaptor.getValue();

            assertEquals(paymentRequest.value(), paymentCaptured.getValue());
            assertEquals(paymentRequest.namePatient(), paymentCaptured.getNamePatient());
            assertEquals(payment.getPatient(), paymentCaptured.getPatient());
            assertEquals(paymentRequest.date(), paymentCaptured.getDateRegister());
            assertEquals(paymentRequest.formOfPayment(), paymentCaptured.getFormOfPayment());
            assertEquals(paymentRequest.nameProfessional(), paymentCaptured.getNameProfessional());
            assertEquals(payment.getProfessionalResponsible(), paymentCaptured.getProfessionalResponsible());
        }

        @Test
        @DisplayName("An exception must be raised when the patient's outstanding balance is greater" +
                " than the value of the procedure")
        void errorOutstandingBalance() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(paymentRequest.namePatient());
            doReturn(professional)
                    .when(professionalService)
                    .findByName(paymentRequest.nameProfessional());


            assertThrows(OutstandingBalanceException.class,
                    () -> paymentService.createPayment(paymentRequest));
        }

        @Test
        @DisplayName("Should throw exception when not finding patient")
        void ShouldThrowExceptionWhenNotFindingPatient() {
            doReturn(null)
                    .when(patientService)
                    .findByName(paymentRequest.namePatient());


            assertThrows(PatientNotFoundException.class,
                    () -> paymentService.createPayment(paymentRequest));
        }

        @Test
        @DisplayName("Should throw exception when not finding professional")
        void ShouldThrowExceptionWhenNotFindingProfessional() {
            doReturn(patient)
                    .when(patientService)
                    .findByName(paymentRequest.namePatient());

            assertThrows(ProfessionalNotFoundException.class,
                    () -> paymentService.createPayment(paymentRequest));
        }
    }

    @Nested
    class findByDia {

        @Test
        @DisplayName("Must return the day's payments")
        void MustReturnTheDaysPayments() {
            var paymentList = List.of(payment);

            doReturn(paymentList)
                    .when(paymentRepository)
                    .findAll();


            var filterList = paymentList.stream().filter(p -> p.getDateRegister()
                            .isAfter(LocalDateTime.now().minusHours(LocalDateTime.now().getHour()))
                            && p.getDateRegister().isBefore(LocalDateTime.now().plusHours(10)))
                    .toList();

            var response = paymentService.findByDia();

            assertNotNull(response);
            assertEquals(filterList.size(), response.size());

        }
    }

    @Nested
    class findByMonth {

        @Test
        @DisplayName("Must return monthly payments")
        void MustReturnMonthlyPayments() {
            var paymentList = List.of(payment);

            doReturn(paymentList)
                    .when(paymentRepository)
                    .findAll();

            var filterList = paymentList.stream().filter(p -> p.getDateRegister().getMonth()
                    .equals(LocalDateTime.now().getMonth())).toList();

            var response = paymentService.findByMounth();

            assertNotNull(response);
            assertEquals(filterList.size(), response.size());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Must delete payment successfully")
        void MustDeletePaymentSuccessfully() {

            doReturn(Optional.of(payment))
                    .when(paymentRepository)
                    .findById(payment.getId());
            doNothing()
                    .when(paymentRepository)
                    .deleteById(payment.getId());

            paymentService.delete(patient.getId());

            verify(paymentRepository, times(1)).findById(payment.getId());
            verify(paymentRepository, times(1)).deleteById(payment.getId());
        }
    }

    @Test
    @DisplayName("Should throw exception when not finding payment")
    void ShouldThrowExceptionWhenNotFindingPayment(){
        doReturn(Optional.empty())
                .when(paymentRepository)
                .findById(payment.getId());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.delete(patient.getId()));

        verify(paymentRepository, times(1)).findById(payment.getId());
        verify(paymentRepository, times(0)).deleteById(payment.getId());
    }
}