package ru.redtoss.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.library.dao.BookDao;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;
import ru.redtoss.library.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final BookDao bookDao;

    @Autowired
    public BooksService(BooksRepository booksRepository, BookDao bookDao) {
        this.booksRepository = booksRepository;
        this.bookDao = bookDao;
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
        updatedBook.setBook_id(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void removeOwner(int id) {
        bookDao.removeOwner(id);
    }

    @Transactional
    public void assignBook(int book_id, int person_id) {
        bookDao.assignBook(book_id, person_id);
    }

    public Optional<Person> getBookOwner(int id) {
        return bookDao.getBookOwner(id);
    }


}
