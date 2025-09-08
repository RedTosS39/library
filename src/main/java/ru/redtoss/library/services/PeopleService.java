package ru.redtoss.library.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.library.dao.PersonDao;
import ru.redtoss.library.models.Book;
import ru.redtoss.library.models.Person;
import ru.redtoss.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    private final PersonDao personDao;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PersonDao personDao) {
        this.peopleRepository = peopleRepository;
        this.personDao = personDao;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPerson_id(id);
        peopleRepository.save(updatedPerson);
    }


    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return personDao.getBooksByPersonId(id);
    }

    @Transactional
    public void setBooks(int book_id, int person_id) {
        personDao.setBooks(book_id, person_id);
    }
}
