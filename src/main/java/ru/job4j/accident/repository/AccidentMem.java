package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();
    private Map<Integer, AccidentType> types = new HashMap<>();
    private int nextId = 1;

    public AccidentMem() {
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
        saveAccident(accidentOne);
        saveAccident(accidentTwo);
        saveAccident(accidentThree);
        saveAccident(accidentFour);
        saveAccident(accidentFive);
    }

    public void saveAccident(Accident accident) {
        int accidentTypeId = accident.getType().getId();
        AccidentType accidentType = types.get(accidentTypeId);
        accident.setType(accidentType);
        if (accident.getId() == 0) {
            accident.setId(nextId);
            accidents.put(nextId++, accident);
        } else {
            accidents.put(accident.getId(), accident);
        }
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
}
