package ru.redtoss.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.library.dao.BookDao;
import ru.redtoss.library.dao.PersonDao;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.Optional;

@Controller
@RequestMapping(value = "/books", produces = "text/html;charset=UTF-8")
public class BookController {

    private final BookDao bookDao;

    private final PersonDao personDao;


    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", bookDao.getBooks());
        return "books/index";
    }

    @GetMapping("{id}")
    public String getBook(@PathVariable("id") int id, Model model,
                          @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.getBook(id));

        Optional<Person> bookOwner = bookDao.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("owner", null);
            model.addAttribute("people", personDao.getPersonList());
        }

        return "books/book";
    }


    @GetMapping("/{id}/edit-book")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBook(id));
        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             @PathVariable("id") int id,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit-book";
        }
        bookDao.editBook(book, id);
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

        bookDao.addBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable(value = "id") int id) {
        bookDao.deleteBook(id);
        return "redirect:/books";
    }

    //назначаем книгу
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id,
                             @RequestParam("person") int person_id) {
        bookDao.assignBook(id, person_id);
        return "redirect:/books/" + id;
    }

    //Освобождаем книгу
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDao.removeOwner(id);
        return "redirect:/books/" + id;
    }
}
