package ru.redtoss.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.List;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

//    void setBooks(int book_id, int person_id);

//    List<Book> findBooks(int person_id);
}
