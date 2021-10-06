package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();
    private int nextId = 1;

    public AccidentMem() {
        Accident accidentOne = new Accident("First accident", "text_1", "address_1");
        Accident accidentTwo = new Accident("Second accident", "text_2", "address_2");
        Accident accidentThree = new Accident("Third accident", "text_3", "address_3");
        Accident accidentFour = new Accident("Fourth accident", "text_4", "address_4");
        Accident accidentFive = new Accident("Fifth accident", "text_5", "address_5");
        saveAccident(accidentOne);
        saveAccident(accidentTwo);
        saveAccident(accidentThree);
        saveAccident(accidentFour);
        saveAccident(accidentFive);
    }

    public void saveAccident(Accident accident) {
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
}
