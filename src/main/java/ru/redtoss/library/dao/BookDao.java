package ru.redtoss.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.redtoss.library.models.Book;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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


    public void addBook() {
        Book book = new Book();
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }
}
