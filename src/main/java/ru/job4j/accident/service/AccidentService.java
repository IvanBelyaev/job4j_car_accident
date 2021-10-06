package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;

import java.util.List;

@Service
public class AccidentService {
    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void saveAccident(Accident accident) {
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
}
