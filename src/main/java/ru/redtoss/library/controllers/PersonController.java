package ru.redtoss.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.library.models.Person;
import ru.redtoss.library.services.PeopleService;

@Controller
@RequestMapping(value = "/people", produces = "text/html;charset=UTF-8")
public class PersonController {

    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping(produces = "text/html;charset=UTF-8")
    public String getPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping(value = "/{person_id}")
    public String getPerson(@PathVariable("person_id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
//        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/person";
    }

    //Переходим по ссылке к форме
    @GetMapping("/add-person")
    public String addPerson(@ModelAttribute("person") Person person) {
        return "people/add-person";
    }

    //Добавляем пользователя
    /*
     * ModelAttribute делает 3 вещи:
     * 1. Создает объект класса Person
     * 2. Заполняет поля класса Person (Person.setName(name) итд)
     * 3. Добавляет класс в Model, с ключем "person"
     */
    @PostMapping
    public String addPersonForm(@ModelAttribute("person") @Valid Person person,
                                Model model,
                                BindingResult bindingResult) {
        model.addAttribute("person", person);
        if (bindingResult.hasErrors()) {
            return "people/add-person";
        }

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit-person")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit-person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               @PathVariable("id") int id,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit-person";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable(value = "id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
