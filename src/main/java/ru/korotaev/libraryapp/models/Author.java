package ru.korotaev.libraryapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int author_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 255, message = "Enter correct name")
    @Column(name = "name")
    private String name;

    @Column(name = "biography")
    private String biography;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author(String name, String biography) {
        this.name = name;
        this.biography = biography;
    }

    public Author() {}

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return author_id == author.author_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(author_id);
    }

}
