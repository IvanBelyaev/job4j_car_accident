package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void saveAccident(Accident accident, String[] ruleIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(accidentMem.getRuleById(Integer.parseInt(id)));
        }
        accident.setRules(rules);

        int accidentTypeId = accident.getType().getId();
        AccidentType accidentType = accidentMem.getAccidentTypeById(accidentTypeId);
        accident.setType(accidentType);

        accidentMem.saveAccident(accident);
    }

    public List<Accident> getAllAccidents() {
        return accidentMem.getAllAccidents();
    }

    public Accident getAccidentById(int id) {
        return accidentMem.getAccidentById(id);
    }

    public List<AccidentType> getAllAccidentTypes() {
        return accidentMem.getAllAccidentTypes();
    }

    public List<Rule> getAllRules() {
        return accidentMem.getAllRules();
    }
}
