package ru.redtoss.library.dao;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBook() {
        String SQL = "SELECT * FROM book";
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Book.class));
    }
}
