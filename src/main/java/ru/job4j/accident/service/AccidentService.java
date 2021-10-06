package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.List;

@Service
public class AccidentService {
    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void addAccident(Accident accident) {
        accidentMem.addAccident(accident);
    }

    public List<Accident> getAllAccidents() {
        return accidentMem.getAllAccidents();
    }
}
