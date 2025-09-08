package ru.redtoss.library.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.List;

@Component
public class PersonDao {


    /// Using Hibernate
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByPersonId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.find(Person.class, id);
        return person.getBooks();
    }

    @Transactional
    public void setBooks(int book_id, int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, book_id);
        Person person = session.find(Person.class, person_id);
        person.getBooks().add(book);
    }



    ///Using JDBC:
/*

private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPersonList() {
        String SQL = "SELECT * FROM person";
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Person createPerson(Person person) {

        jdbcTemplate.update("INSERT INTO person (name, age) VALUES (?, ?)", person.getName(), person.getAge());
        return person;
    }

    public void updatePerson(Person person, int id) {
        jdbcTemplate.update("UPDATE  person SET name=?, age=? WHERE person_id=?", person.getName(), person.getAge(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }*/
}
