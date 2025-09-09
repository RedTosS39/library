package ru.redtoss.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;
import ru.redtoss.library.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;

    }


    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return booksRepository.findById(id);
    }


    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setBook_id(id);
        bookToBeUpdated.setOwner(updatedBook.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }


    public Optional<Person> getBookOwner(int id) {
        Optional<Person> owner = booksRepository.findOwnerById(id);
        if (owner.isPresent()) {
            Hibernate.initialize(owner.get());
            return owner;
        } else {
            return Optional.empty();
        }
    }
}
