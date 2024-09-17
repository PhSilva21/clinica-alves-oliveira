package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.ProfessionalRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateProfessionalDTO;
import com.bandeira.clinica_alves_oliveira.models.Professional;
import com.bandeira.clinica_alves_oliveira.models.Query;
import com.bandeira.clinica_alves_oliveira.services.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @PostMapping
    public ResponseEntity<ProfessionalRequest> createProfessional(@RequestBody @Valid ProfessionalRequest
                                                                professionalRequest) throws IOException {
        var response = professionalService.createProfessional(professionalRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/findByQueriesByProfessional")
    public ResponseEntity<List<Query>> findByQueriesByProfessional(@RequestParam @Param("nomeProfessional")
                                                             String nomeProfessional){
        var response = professionalService.queryLIst(nomeProfessional);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Professional>> findAll(){
        var response = professionalService.findAll();
        return  ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateById(@PathVariable Long id,
                                           UpdateProfessionalDTO updateProfessionalDTO) throws IOException {
        professionalService.update(updateProfessionalDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        professionalService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
