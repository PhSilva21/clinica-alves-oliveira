package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.ProcedureRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateProceduresDTO;
import com.bandeira.clinica_alves_oliveira.models.Procedure;
import com.bandeira.clinica_alves_oliveira.services.ProcedureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("procedimento")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    @PostMapping
    public ResponseEntity<ProcedureRequest> createProcedure(@RequestBody @Valid ProcedureRequest procedureRequest){
        var response = procedureService.createProcedure(procedureRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Procedure>> findbyId(@PathVariable Long id){
        var response = procedureService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Procedure>> findAll(){
        var response = procedureService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id,
                                           UpdateProceduresDTO updateProceduresDTO){
        procedureService.update(id, updateProceduresDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        procedureService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
