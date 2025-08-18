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
    public String getBook(@PathVariable("id") int id,
                          @ModelAttribute("person") Person person,
                          Model model) {
        model.addAttribute("book", bookDao.getBook(id));


        Optional<Person> owner = bookDao.getBookOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDao.getPersonList());
            System.out.println(personDao.getPersonList());
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
        book.setBook_id(id);
        bookDao.editBook(book);
        return "redirect:/books";
    }


    /*
     * ModelAttribute делает 3 вещи:
     * 1. Создает объект класса
     * 2. Заполняет поля класса  (.setName(name) итд)
     * 3. Добавляет класс в Model, с ключем "book"
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


    //Освобождаем книгу
    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }


    //назначаем книгу
    @PostMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson, Model model) {
        bookDao.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
