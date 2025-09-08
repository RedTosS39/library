package ru.redtoss.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;
import ru.redtoss.library.services.BooksService;
import ru.redtoss.library.services.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/books", produces = "text/html;charset=UTF-8")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", booksService.getBooks());
        return "books/index";
    }

    @GetMapping("{id}")
    public String getBook(@PathVariable("id") int id,
                          @ModelAttribute("person") Person person,
                          Model model) {

        model.addAttribute("book", booksService.getBookById(id));
        Optional<Person> bookOwner = booksService.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("owner", null);
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/book";
    }


    @GetMapping("/{id}/edit-book")
    public String editBook(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("book", booksService.getBookById(id));

        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             @PathVariable("id") int id,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit-book";
        }
        booksService.updateBook(id, book);
        return "redirect:/books";
    }


    /*
     * ModelAttribute делает 3 вещи:
     * 1. Создает объект класса Person
     * 2. Заполняет поля класса Person (Person.setName(name) итд)
     * 3. Добавляет класс в Model, с ключем "person"
     */

    //show form
    @GetMapping("/add-book")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/add-book";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          Model model,
                          BindingResult bindingResult) {

        model.addAttribute("book", book);
        if (bindingResult.hasErrors()) {
            return "books/add-book";
        }
        booksService.saveBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable(value = "id") int id) {
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    //назначаем книгу
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id,
                             @RequestParam("person") int person_id) {
        booksService.assignBook(id, person_id);
        return "redirect:/books/" + id;
    }

    //Освобождаем книгу
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.removeOwner(id);
        return "redirect:/books/" + id;
    }
}
