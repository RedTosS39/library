package ru.redtoss.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;
    private final PersonDao personDao;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate, PersonDao personDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDao = personDao;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("Select * from book where book_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void editBook(Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE book_id=?",
                book.getTitle(), book.getAuthor(), book.getYear(), book.getBook_id());
    }


    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }


    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT p.* FROM person p JOIN book b ON p.person_id = b.person_id WHERE book_id = ?",
                new BeanPropertyRowMapper<>(Person.class),
                id
        ).stream().findAny();
    }

    //возвращаем книгу с библиотеки
    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE book_id = ?", id);
    }

    //берем книгу из библиотеки
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", selectedPerson.getPerson_id(), id);
    }
}
