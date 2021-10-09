package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private AccidentJdbcTemplate accidentJdbcTemplate;

    public AccidentService(AccidentJdbcTemplate accidentJdbcTemplate) {
        this.accidentJdbcTemplate = accidentJdbcTemplate;
    }

    public void saveAccident(Accident accident, String[] ruleIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(accidentJdbcTemplate.getRuleById(Integer.parseInt(id)));
        }
        accident.setRules(rules);

        int accidentTypeId = accident.getType().getId();
        AccidentType accidentType = accidentJdbcTemplate.getAccidentTypeById(accidentTypeId);
        accident.setType(accidentType);

        accidentJdbcTemplate.saveAccident(accident);
    }

    public List<Accident> getAllAccidents() {
        return accidentJdbcTemplate.getAllAccidents();
    }

    public Accident getAccidentById(int id) {
        return accidentJdbcTemplate.getAccidentById(id);
    }

    public List<AccidentType> getAllAccidentTypes() {
        return accidentJdbcTemplate.getAllAccidentTypes();
    }

    public List<Rule> getAllRules() {
        return accidentJdbcTemplate.getAllRules();
    }
}
