package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AccidentController {
    private final AccidentService accidentService;

    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<AccidentType> types = accidentService.getAllAccidentTypes();
        List<Rule> rules = accidentService.getAllRules();
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "create";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        Accident accident = accidentService.getAccidentById(id);
        List<AccidentType> types = accidentService.getAllAccidentTypes();
        List<Rule> rules = accidentService.getAllRules();
        model.addAttribute("accident", accident);
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ruleIds = req.getParameterValues("rIds");
        accidentService.saveAccident(accident, ruleIds);
        return "redirect:/";
    }
}
