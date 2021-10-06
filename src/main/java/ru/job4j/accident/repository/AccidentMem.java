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

    public AccidentMem() {
        Accident accidentOne = new Accident(1, "First accident", "text_1", "address_1");
        Accident accidentTwo = new Accident(2, "Second accident", "text_2", "address_2");
        Accident accidentThree = new Accident(3, "Third accident", "text_3", "address_3");
        Accident accidentFour = new Accident(4, "Fourth accident", "text_4", "address_4");
        Accident accidentFive = new Accident(5, "Fifth accident", "text_5", "address_5");
        addAccident(accidentOne);
        addAccident(accidentTwo);
        addAccident(accidentThree);
        addAccident(accidentFour);
        addAccident(accidentFive);
    }

    public void addAccident(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public List<Accident> getAllAccidents() {
        return new ArrayList<>(accidents.values());
    }
}
