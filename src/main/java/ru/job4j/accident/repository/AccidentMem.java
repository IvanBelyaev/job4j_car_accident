package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    public AccidentMem() {
        Rule ruleOne = Rule.of(1, "Статья 1");
        Rule ruleTwo = Rule.of(2, "Статья 2");
        Rule ruleThree = Rule.of(3, "Статья 3");
        rules.put(ruleOne.getId(), ruleOne);
        rules.put(ruleTwo.getId(), ruleTwo);
        rules.put(ruleThree.getId(), ruleThree);

        AccidentType accidentTypeOne = AccidentType.of(1, "Две машины");
        AccidentType accidentTypeTwo = AccidentType.of(2, "Машина и человек");
        AccidentType accidentTypeThree = AccidentType.of(3, "Машина и велосипед");
        types.put(accidentTypeOne.getId(), accidentTypeOne);
        types.put(accidentTypeTwo.getId(), accidentTypeTwo);
        types.put(accidentTypeThree.getId(), accidentTypeThree);

        Accident accidentOne = new Accident("First accident", "text_1", "address_1", accidentTypeOne);
        Accident accidentTwo = new Accident("Second accident", "text_2", "address_2", accidentTypeTwo);
        Accident accidentThree = new Accident("Third accident", "text_3", "address_3", accidentTypeThree);
        Accident accidentFour = new Accident("Fourth accident", "text_4", "address_4", accidentTypeOne);
        Accident accidentFive = new Accident("Fifth accident", "text_5", "address_5", accidentTypeTwo);
        accidentOne.setRules(Set.of(ruleOne, ruleTwo));
        accidentTwo.setRules(Set.of(ruleTwo, ruleThree));
        accidentThree.setRules(Set.of(ruleOne, ruleThree));
        accidentFour.setRules(Set.of(ruleOne, ruleTwo, ruleThree));
        accidentFive.setRules(Set.of(ruleThree));

        saveAccident(accidentOne);
        saveAccident(accidentTwo);
        saveAccident(accidentThree);
        saveAccident(accidentFour);
        saveAccident(accidentFive);
    }

    public void saveAccident(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(nextId.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }

    public List<Accident> getAllAccidents() {
        return new ArrayList<>(accidents.values());
    }

    public Accident getAccidentById(int id) {
        return accidents.get(id);
    }

    public List<AccidentType> getAllAccidentTypes() {
        return new ArrayList<>(types.values());
    }

    public AccidentType getAccidentTypeById(int id) {
        return types.get(id);
    }

    public List<Rule> getAllRules() {
        return new ArrayList<>(rules.values());
    }

    public Rule getRuleById(int id) {
        return rules.get(id);
    }
}
