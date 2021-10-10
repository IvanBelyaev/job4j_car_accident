package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private AccidentHibernate accidentHibernate;

    public AccidentService(AccidentHibernate accidentHibernate) {
        this.accidentHibernate = accidentHibernate;
    }

    public void saveAccident(Accident accident, String[] ruleIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(accidentHibernate.getRuleById(Integer.parseInt(id)));
        }
        accident.setRules(rules);

        int accidentTypeId = accident.getType().getId();
        AccidentType accidentType = accidentHibernate.getAccidentTypeById(accidentTypeId);
        accident.setType(accidentType);

        accidentHibernate.saveAccident(accident);
    }

    public List<Accident> getAllAccidents() {
        return accidentHibernate.getAllAccidents();
    }

    public Accident getAccidentById(int id) {
        return accidentHibernate.getAccidentById(id);
    }

    public List<AccidentType> getAllAccidentTypes() {
        return accidentHibernate.getAllAccidentTypes();
    }

    public List<Rule> getAllRules() {
        return accidentHibernate.getAllRules();
    }
}
