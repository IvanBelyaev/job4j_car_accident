package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional("transactionManager")
public class AccidentService {
    private AccidentRepository accidentRepository;
    private AccidentTypeRepository accidentTypeRepository;
    private RuleRepository ruleRepository;

    public AccidentService(
            AccidentRepository accidentRepository,
            AccidentTypeRepository accidentTypeRepository,
            RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.accidentTypeRepository = accidentTypeRepository;
        this.ruleRepository = ruleRepository;
    }

    public void saveAccident(Accident accident, String[] ruleIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(ruleRepository.findById(Integer.parseInt(id)).get());
        }
        accident.setRules(rules);

        int accidentTypeId = accident.getType().getId();
        AccidentType accidentType = accidentTypeRepository.findById(accidentTypeId).get();
        accident.setType(accidentType);

        accidentRepository.save(accident);
    }

    public List<Accident> getAllAccidents() {
        Iterable<Accident> accidents = accidentRepository.findAll();
        List<Accident> result = new ArrayList<>();
        accidents.forEach(result::add);
        return result;
    }

    public Accident getAccidentById(int id) {
        return accidentRepository.findById(id).get();
    }

    public List<AccidentType> getAllAccidentTypes() {
        Iterable<AccidentType> types = accidentTypeRepository.findAll();
        List<AccidentType> result = new ArrayList<>();
        types.forEach(result::add);
        return result;
    }

    public List<Rule> getAllRules() {
        Iterable<Rule> rules = ruleRepository.findAllByOrderByIdAsc();
        List<Rule> result = new ArrayList<>();
        rules.forEach(result::add);
        return result;
    }
}
