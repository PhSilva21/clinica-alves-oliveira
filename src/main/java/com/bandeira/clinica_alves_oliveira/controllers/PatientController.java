package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.PatientRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdatePatientDTO;
import com.bandeira.clinica_alves_oliveira.models.Patient;
import com.bandeira.clinica_alves_oliveira.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "paciente")
public class PatientController {

    @Autowired
    private PatientService pacienteService;

    @PostMapping("/register")
    public ResponseEntity<PatientRequest> create(@RequestBody @Valid PatientRequest pacienteRequest) throws IOException {
        var response = pacienteService.createPatient(pacienteRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/teste")
    public List<Patient> lista(){
        return pacienteService.findAll();
    }

    @GetMapping
    public List<Patient> ani(){
        return pacienteService.birthdays();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> updatePacienteById(@PathVariable ("id") Long id,
                                                   @RequestBody UpdatePatientDTO updatePacienteDTO) throws IOException {
        pacienteService.update(id, updatePacienteDTO);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleById(@PathVariable ("id") Long id){
        pacienteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{nome}")
    public ResponseEntity<Patient>finByNome(@PathVariable ("nome") String nome){
        var response = pacienteService.findByName(nome);
        return ResponseEntity.ok().body(response);
    }

}

