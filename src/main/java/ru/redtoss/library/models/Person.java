package ru.redtoss.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @NotEmpty(message = "Поле Имя должно быть заполнено")
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;


    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Person(String name, int age, List<Book> books) {
        this.name = name;
        this.age = age;
        this.books = books;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Person() {
    }

    //    @NotEmpty(message = "Field name have to be initialize")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
               "id=" + getPerson_id() +
               ", name='" + getName() + '\'' +
               ", age=" + getAge() +
               '}';
    }
}
