package ru.redtoss.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PeopleBookService {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public PeopleBookService(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


}
