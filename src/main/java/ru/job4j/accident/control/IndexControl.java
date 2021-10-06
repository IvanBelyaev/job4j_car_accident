package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        List<String> listOfItems = List.of("string_1", "string_2", "string_3", "string_4", "string_5");
        model.addAttribute("items", listOfItems);
        return "index";
    }
}
