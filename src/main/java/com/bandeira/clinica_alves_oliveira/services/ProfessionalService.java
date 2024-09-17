package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.ProfessionalRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateProfessionalDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Professional;
import com.bandeira.clinica_alves_oliveira.models.Query;
import com.bandeira.clinica_alves_oliveira.models.StatusQuery;
import com.bandeira.clinica_alves_oliveira.repositories.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ViaCepService viaCepService;


    public ProfessionalRequest createProfessional(ProfessionalRequest professionalRequest) throws IOException {

        var address = viaCepService.getAddress(professionalRequest.cep());

        Professional professional = new Professional(
                professionalRequest.name(),
                professionalRequest.dateOfBirth(),
                professionalRequest.rg(),
                professionalRequest.cpf(),
                professionalRequest.profession(),
                professionalRequest.cep(),
                address.getUf(),
                address.getLocalidade(),
                address.getLogradouro(),
                professionalRequest.number(),
                address.getBairro(),
                professionalRequest.complement(),
                professionalRequest.email(),
                professionalRequest.cel(),
                professionalRequest.tel(),
                professionalRequest.bank(),
                professionalRequest.agency(),
                professionalRequest.account());

        Professional save = professionalRepository.save(professional);

        return professionalRequest;
	}


    public List<Query> queryLIst(String professionalName){

        var professional = professionalRepository.findByName(professionalName);

        if(professional == null){
            throw new ProfessionalNotFoundException();
        }

        return professional.getQueries().stream()
                .filter(c -> c.getStatusQuery() != StatusQuery.SERVED)
                .collect(Collectors.toList());
    }


    public List<Professional> findAll(){
        return professionalRepository.findAll();
    }


    public Professional findByName(String name){
        return professionalRepository.findByName(name);
    }


    public void update(UpdateProfessionalDTO updateProfessionalDTO) throws IOException {

        var professional = professionalRepository.findById(updateProfessionalDTO.id())
                .orElseThrow(ProfessionalNotFoundException::new);

        professional.setName(updateProfessionalDTO.name());
        professional.setDateOfBirth(updateProfessionalDTO.dateOfBirth());
        professional.setRg(updateProfessionalDTO.rg());
        professional.setCpf(updateProfessionalDTO.cpf());
        professional.setProfession(updateProfessionalDTO.profession());
        professional.setCep(updateProfessionalDTO.cep());

        var address = viaCepService.getAddress(updateProfessionalDTO.cep());

        professional.setState(address.getUf());
        professional.setCity(address.getLocalidade());
        professional.setStreet(address.getLogradouro());
        professional.setNumber(updateProfessionalDTO.number());
        professional.setNeighborhood(address.getBairro());
        professional.setComplement(updateProfessionalDTO.complement());
        professional.setEmail(updateProfessionalDTO.email());
        professional.setCel(updateProfessionalDTO.cel());
        professional.setTel(updateProfessionalDTO.tel());
        professional.setBank(updateProfessionalDTO.bank());
        professional.setAgency(updateProfessionalDTO.agency());
        professional.setAccount(updateProfessionalDTO.account());

        professionalRepository.save(professional);
    }


    public void deleteById(Long id) {

        var professional = professionalRepository
                .findById(id).orElseThrow(ProfessionalNotFoundException::new);

        professionalRepository.deleteById(id);
    }
}
