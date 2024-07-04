package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.PatientRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePatientDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.AddressNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Patient;
import com.bandeira.clinica_alves_oliveira.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ViaCepService viaCepService;

    public PatientRequest createPatient(PatientRequest patientRequest) throws IOException {

        var address = viaCepService.getAddress(patientRequest.cep());


        Patient patient = new Patient(
                patientRequest.name(),
                patientRequest.cpf(),
                patientRequest.rg(),
                patientRequest.dateOfBirth(),
                patientRequest.cep(),
                address.getUf(),
                address.getLocalidade(),
                address.getLogradouro(),
                patientRequest.number(),
                address.getBairro(),
                patientRequest.complement(),
                patientRequest.email(),
                patientRequest.cel(),
                patientRequest.tel(),
                patientRequest.responsible(),
                patientRequest.cpfOfResponsible());

        patientRepository.save(patient);

        return patientRequest;
    }

    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();

    }


    public void update(Long id, UpdatePatientDTO updatePatientDTO) throws IOException {

        var patient = patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);

        patient.setName(updatePatientDTO.name());
        patient.setCpf(updatePatientDTO.cpf());
        patient.setRg(updatePatientDTO.rg());
        patient.setDateOfBirth(updatePatientDTO.dateOfBirth());
        patient.setCep(updatePatientDTO.cep());

        var address = viaCepService.getAddress(updatePatientDTO.cep());

        if(address == null){
            throw new AddressNotFoundException();
        }

        patient.setState(address.getUf());
        patient.setCity(address.getLocalidade());
        patient.setStreet(address.getLogradouro());
        patient.setNumber(updatePatientDTO.number());
        patient.setNeighborhood(address.getBairro());
        patient.setComplement(updatePatientDTO.complement());
        patient.setEmail(updatePatientDTO.email());
        patient.setCel(updatePatientDTO.cel());
        patient.setTel(updatePatientDTO.tel());
        patient.setResponsible(updatePatientDTO.responsible());
        patient.setCpfOfResposible(updatePatientDTO.cpfOfResponsible());

        patientRepository.save(patient);

    }


    public void deleteById(Long id) {

        var userExists = patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);

        patientRepository.deleteById(id);
    }

    public Patient findByName(String nome){
        return patientRepository.findByName(nome);
    }


    public List<Patient> birthdays() {
        return findAll().stream().filter(p -> p.getDateOfBirth()
                        .equals(LocalDate.now()))
                        .collect(Collectors.toList());
    }
}
