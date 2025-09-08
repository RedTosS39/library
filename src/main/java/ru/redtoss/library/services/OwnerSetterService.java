package ru.redtoss.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;
import ru.redtoss.library.repositories.BooksRepository;
import ru.redtoss.library.repositories.PeopleRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OwnerSetterService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;


    @Autowired
    public OwnerSetterService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }


    @Transactional
    public void setOwner(int book_id, int person_id) {
        Optional<Book> book = booksRepository.findById(book_id);
        Optional<Person> person = peopleRepository.findById(person_id);
        if (book.isPresent() && person.isPresent()) {
            Hibernate.initialize(person.get());
            Hibernate.initialize(book.get());
            Book bk = book.get();
            Person p = person.get();
            bk.setOwner(p);
        }
    }


    @Transactional
    public void removeOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setOwner(null);
        }
    }


}
