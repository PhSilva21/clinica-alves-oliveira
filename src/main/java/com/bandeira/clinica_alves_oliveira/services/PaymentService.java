package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.PaymentRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePaymentDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.OutstandingBalanceException;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.PaymentNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Payment;
import com.bandeira.clinica_alves_oliveira.repositories.PatientRepository;
import com.bandeira.clinica_alves_oliveira.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ProfessionalService professionalService;

    public PaymentRequest createPayment(PaymentRequest paymentRequest) {

        var patient = patientService.findByName(paymentRequest.namePatient());

        if (patient == null) {
            throw new PatientNotFoundException();
        }

        var professional = professionalService.findByName(paymentRequest.nameProfessional());

        if (professional == null) {
            throw new ProfessionalNotFoundException();
        }

        Payment payment = new Payment(

                paymentRequest.value(),
                paymentRequest.namePatient(),
                patient,
                paymentRequest.date(),
                paymentRequest.formOfPayment(),
                paymentRequest.nameProfessional(),
                professional
        );

        if(patient.getAmountReceived() == null) {
            patient.setAmountReceived(paymentRequest.value());
        } else
            patient.setAmountReceived(patient.getAmountReceived() + payment.getValue());

        if (patient.getOutstandingBalance() == null){
            patient.setOutstandingBalance(0.0);
            if(patient.getOutstandingBalance() < paymentRequest.value()){
                throw new OutstandingBalanceException();
            }
        } else
            patient.setOutstandingBalance(patient.getOutstandingBalance() - paymentRequest.value());

        patientRepository.save(patient);

        paymentRepository.save(payment);

        return paymentRequest;
    }



    public void update(Long id, UpdatePaymentDTO updatePaymentDTO) {

        var payment = paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);

        payment.setValue(updatePaymentDTO.value());

        payment.setNamePatient(updatePaymentDTO.namePatient());

        var patient= patientService.findByName(updatePaymentDTO.namePatient());

        if (patient == null) {
            throw new PatientNotFoundException();
        }

        if (patient.getOutstandingBalance() == null) {
            patient.setOutstandingBalance(0.0);
        }
        if(patient.getOutstandingBalance() < updatePaymentDTO.value()){
            throw new OutstandingBalanceException();
        }
            patient.setOutstandingBalance(patient.getOutstandingBalance() - updatePaymentDTO.value());

        payment.setPatient(patient);

        payment.setDateRegister(updatePaymentDTO.dateRegister());
        payment.setFormOfPayment(updatePaymentDTO.formOfPayment());

        payment.setNameProfessional(updatePaymentDTO.nameProfessional());

        var professional = professionalService.findByName(updatePaymentDTO.nameProfessional());

        if (professional == null) {
            throw new ProfessionalNotFoundException();
        }

        payment.setProfessionalResponsible(professional);


        paymentRepository.save(payment);
    }



    public List<Payment> findByDia(){
        return paymentRepository.findAll().stream().filter(p -> p.getDateRegister()
                .isAfter(LocalDateTime.now().minusHours(LocalDateTime.now().getHour()))
                && p.getDateRegister().isBefore(LocalDateTime.now().plusHours(10)))
                .collect(Collectors.toList());
    }

    public List<Payment> findByMounth(){
        return paymentRepository.findAll().stream().filter(p -> p.getDateRegister().getMonth()
                .equals(LocalDateTime.now().getMonth())).collect(Collectors.toList());
    }

    public void delete(Long id){

        var payment = paymentRepository.findById(id).orElseThrow(PatientNotFoundException::new);

        paymentRepository.deleteById(id);
    }
}
