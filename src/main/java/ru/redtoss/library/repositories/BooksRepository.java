package ru.redtoss.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b.owner FROM Book b WHERE b.book_id = :bookId")
    Optional<Person> findOwnerByBookId(@Param("bookId") int bookId);

}
