package ru.korotaev.libraryapp.dtos;

import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;

public class AddBookDTO {
    private Book book;
    private Author author;

    public AddBookDTO(Book book, Author author) {
        this.book = book;
        this.author = author;
    }

    public AddBookDTO() {}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
