package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.QueryRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateQueryDTO;
import com.bandeira.clinica_alves_oliveira.models.Query;
import com.bandeira.clinica_alves_oliveira.services.QueryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("queries")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @PostMapping
    public ResponseEntity<QueryRequest> createQuery(@RequestBody QueryRequest queryRequest){
        var response = queryService.createQuery(queryRequest);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping
    public ResponseEntity<List<Query>> findAll(){
        var response = queryService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id,@RequestBody @Valid UpdateQueryDTO updateQueryDTO){
        queryService.update(id, updateQueryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deteleById(@PathVariable Long id){
        queryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Query>> findById(@PathVariable Long id){
        var response = queryService.findById(id);
        return ResponseEntity.ok().body(response);
    }
}
