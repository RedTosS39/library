package ru.redtoss.library.models;

import jakarta.validation.constraints.NotEmpty;

public class Book {

    private int book_id;
    private String title;
    private String author;
    private int year;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Book(int book_id, String title, String author, int year, String owner) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + book_id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", year='" + year + '\'' +
               '}';
    }
}
