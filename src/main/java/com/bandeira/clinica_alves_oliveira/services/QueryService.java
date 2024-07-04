package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.QueryRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateQueryDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.QueryNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Patient;
import com.bandeira.clinica_alves_oliveira.models.Professional;
import com.bandeira.clinica_alves_oliveira.models.Query;
import com.bandeira.clinica_alves_oliveira.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private PatientService patientService;


    public QueryRequest createQuery(QueryRequest queryRequest){

        Patient patient = patientService.findByName(queryRequest.namePatient());

        if(patient == null){
            throw new PatientNotFoundException();
        }

        Professional professional = professionalService.findByName(queryRequest.nameProfessional());

        if(professional == null){
            throw new ProfessionalNotFoundException();
        }

        Query query = new Query(
                queryRequest.namePatient(),
                queryRequest.nameProfessional(),
                patient,
                professional,
                queryRequest.time(),
                queryRequest.duration(),
                queryRequest.statusQuery(),
                patient.getCel());

        queryRepository.save(query);

        return queryRequest;
    }

    public List<Query> findAll() {

        Sort sort = Sort.by("time").ascending();

        return queryRepository.findAll(sort);
    }

    public void update(Long id, UpdateQueryDTO updateQueryDTO){

        var query = queryRepository.findById(id).orElseThrow(QueryNotFoundException::new);

        var patient = patientService.findByName(updateQueryDTO.namePatient());

        if(patient == null){
            throw new PatientNotFoundException();
        }

        Professional professional = professionalService.findByName(updateQueryDTO.nameProfessional());

        if(professional == null){
            throw new ProfessionalNotFoundException();
        }

        query.setTime(updateQueryDTO.time());
        query.setDuration(updateQueryDTO.duration());
        query.setStatusQuery(updateQueryDTO.statusQuery());
        query.setPatientCel(patient.getCel());
        query.setNamePatient(updateQueryDTO.namePatient());
        query.setPatient(patient);
        query.setNameProfessional(updateQueryDTO.nameProfessional());
        query.setProfessional(professional);

        queryRepository.save(query);
    }

    public void deleteById(Long id) {

        var consulta = queryRepository.findById(id).orElseThrow(QueryNotFoundException::new);

        queryRepository.deleteById(id);

    }

    public Optional<Query> findById(Long id) {
        var query = queryRepository.findById(id);

        return query;
    }
}

