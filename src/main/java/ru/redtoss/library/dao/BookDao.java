package ru.redtoss.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).getResultList();
    }


    @Transactional(readOnly = true)
    public Book getBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Book.class, id);
    }

    @Transactional
    public void editBook(Book book, int id) {
        Session session = sessionFactory.getCurrentSession();
        Book editedBook = session.find(Book.class, id);
        editedBook.setTitle(book.getTitle());
        editedBook.setAuthor(book.getAuthor());
        editedBook.setYear(book.getYear());
    }


    @Transactional
    public void addBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }


    @Transactional
    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.find(Book.class, id));
    }

    @Transactional(readOnly = true)
    public Optional<Person> getBookOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, id);
        return Optional.ofNullable(book.getOwner());
    }


    @Transactional
    public void removeOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void assignBook(int book_id, int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, book_id);
        Person owner = session.find(Person.class, person_id);
        book.setOwner(owner);
    }

    ///JDBCTemplate
   /* private final JdbcTemplate jdbcTemplate;

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


    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }*/


}
