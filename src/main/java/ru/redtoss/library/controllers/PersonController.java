package ru.redtoss.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.redtoss.library.dao.PersonDao;
import ru.redtoss.library.models.Person;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDao personDao;

    @Autowired
    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String getPeople(Model model) {
        List<Person> people = personDao.getPersonList();
        model.addAttribute("people", people);
        System.out.println("Передано людей в шаблон: " + people.size());  // Лог
        return "people/index";
    }
}
