package ru.redtoss.library.models;

import jakarta.validation.constraints.NotEmpty;

public class Person {

    private int person_id;

    @NotEmpty(message = "Поле Имя должно быть заполненно")
    private String name;
    private int age;


    public Person(int person_id, String name, int age) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
    }

    public Person() {}

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

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
               "id=" + person_id +
               ", name='" + name + '\'' +
               ", age=" + age +
               '}';
    }
}
