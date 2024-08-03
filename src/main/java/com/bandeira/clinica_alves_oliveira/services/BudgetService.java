package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.BudgetRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateBudgetDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.BudgetNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.PatientNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProcedureNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ProfessionalNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Budget;
import com.bandeira.clinica_alves_oliveira.models.StatusBudget;
import com.bandeira.clinica_alves_oliveira.repositories.BudgetRepository;
import com.bandeira.clinica_alves_oliveira.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    public ProcedureService procedureService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ProfessionalService professionalService;



    public Budget createBudget(BudgetRequest budgetRequest) {

        var patient = patientService.findByName(budgetRequest.namePatient());

        if(patient == null){
            throw new PatientNotFoundException();
        }

        var professional = professionalService.findByName(budgetRequest.nameProfessional());

        if(professional == null){
            throw new ProfessionalNotFoundException();
        }

        var procedure = procedureService.findByName(budgetRequest.nameProcedure());

        if (procedure == null){
            throw new ProcedureNotFoundException();
        }

        Budget budget = new Budget(
                budgetRequest.dateRegister(),
                budgetRequest.statusBudget(),
                budgetRequest.namePatient(),
                budgetRequest.nameProfessional(),
                budgetRequest.nameProcedure(),
                patient,
                professional,
                procedure.getValue(),
                procedure
        );

        if(budget.getStatusBudget().equals(StatusBudget.CONTRACTED)) {
            patient.setOutstandingBalance(patient.getOutstandingBalance().add(budget.getValue()));
        }

        patientRepository.save(patient);

        budgetRepository.save(budget);

        return budget;
    }

    public List<Budget> findAll(){
        return budgetRepository.findAll();
    }


    public Optional<Budget> findById(Long id) {
        return budgetRepository.findById(id);
    }


    public void update(Long id, UpdateBudgetDTO updateBudgetDTO) {

        var budget = budgetRepository.findById(id).orElseThrow(BudgetNotFoundException::new);

        budget.setDateRegister(updateBudgetDTO.dateRegister());
        budget.setStatusBudget(updateBudgetDTO.statusBudget());
        budget.setNamePatient(updateBudgetDTO.namePatient());
        budget.setNameProfessional(updateBudgetDTO.nameProfessional());
        var patient = patientService.findByName(updateBudgetDTO.namePatient());

        if(patient == null){
            throw new PatientNotFoundException();
        }

        var professional = professionalService.findByName(updateBudgetDTO.nameProfessional());

        if(professional == null){
            throw new ProfessionalNotFoundException();
        }

        var procedure = procedureService.findByName(updateBudgetDTO.nameProcedure());

        if(procedure == null){
            throw new ProcedureNotFoundException();
        }

        budget.setPatient(patient);
        budget.setProfessional(professional);
        budget.setValue(procedure.getValue());

        if(budget.getStatusBudget().equals(StatusBudget.CONTRACTED)){
            patient.setOutstandingBalance(budget.getValue());
        }

        patientRepository.save(patient);

        budgetRepository.save(budget);
    }


    public void deleteById(Long id) {

        var userExists = budgetRepository.findById(id).orElseThrow(BudgetNotFoundException::new);

        budgetRepository.deleteById(id);
    }
}
