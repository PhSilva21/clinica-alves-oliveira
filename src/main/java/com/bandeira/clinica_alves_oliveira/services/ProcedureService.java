package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.ProcedureRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateProceduresDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.ProcedureNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Procedure;
import com.bandeira.clinica_alves_oliveira.repositories.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;


    public ProcedureRequest createProcedure(ProcedureRequest procedureRequest) {

        Procedure procedure = new Procedure(
                procedureRequest.description(),
                procedureRequest.specialty(),
                procedureRequest.value());

        procedureRepository.save(procedure);

        return procedureRequest;
    }



    public Optional<Procedure> findById(Long id) {
        return procedureRepository.findById(id);
    }



    public List<Procedure> findAll() {
        return procedureRepository.findAll();

    }



    public void update(Long id, UpdateProceduresDTO updateProceduresDTO) {

        var procedure = procedureRepository.findById(id)
                .orElseThrow(ProcedureNotFoundException::new);

        procedure.setDescription(updateProceduresDTO.description());
        procedure.setSpecialty(updateProceduresDTO.specialty());
        procedure.setValue(updateProceduresDTO.value());

        procedureRepository.save(procedure);
    }



    public Procedure findByName(String description){
        var procedure = procedureRepository.findByDescription(description);
        if (procedure == null) {
            throw new ProcedureNotFoundException();
        }
        return procedure;
    }


    public void deleteById(Long id) {

        var procedure = procedureRepository.findById(id)
                .orElseThrow(ProcedureNotFoundException::new);

        procedureRepository.deleteById(id);
    }
}
