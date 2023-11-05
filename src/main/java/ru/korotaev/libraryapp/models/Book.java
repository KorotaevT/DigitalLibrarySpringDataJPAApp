package ru.korotaev.libraryapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 255, message = "Enter correct name")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Author should not be empty")
    @Size(max = 255, message = "Enter correct name")
    @Column(name = "author_id")
    private int author_id;

    @Column(name = "description")
    private String description;

    public Book(String name, int author_id, String desription) {
        this.name = name;
        this.author_id = author_id;
        this.description = desription;
    }

    public Book() {}

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desription) {
        this.description = desription;
    }
}
