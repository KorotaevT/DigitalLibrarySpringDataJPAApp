package ru.korotaev.libraryapp.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private Long isbn;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Enter correct name")
    private String book;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Enter correct name")
    private String author;

    private int year;

    public Book(String book, String author, int year) {
        this.book = book;
        this.author = author;
        this.year = year;
    }

    public Book() {}

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

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
}
