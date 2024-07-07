package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.PatientRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePatientDTO;
import com.bandeira.clinica_alves_oliveira.models.Patient;
import com.bandeira.clinica_alves_oliveira.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<PatientRequest> create(@RequestBody @Valid PatientRequest patientRequest) throws IOException {
        var response = patientService.createPatient(patientRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/birthdays")
    public ResponseEntity<List<Patient>> birthdays(){
        var response = patientService.birthdays();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Patient>> findAll(){
        var response = patientService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updatePatientById(@PathVariable ("id") Long id,
                                                   @RequestBody UpdatePatientDTO updatePatientDTO) throws IOException {
        patientService.update(id, updatePatientDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ("id") Long id){
        patientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/findByName")
    public ResponseEntity<Patient>finByName(@RequestParam @Param("name") String name){
        var response = patientService.findByName(name);
        return ResponseEntity.ok().body(response);
    }

}

