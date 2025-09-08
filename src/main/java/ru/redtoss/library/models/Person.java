package ru.redtoss.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

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
