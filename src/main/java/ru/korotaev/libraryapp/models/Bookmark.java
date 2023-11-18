package ru.korotaev.libraryapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookmark_id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(max = 255, message = "Enter correct name")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Страница не должно быть пустой")
    @Column(name = "page")
    private int page;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    @NotNull(message = "Книга не должна быть пустой")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @NotNull(message = "Клиент не должно быть пустым")
    private User user;

    public Bookmark(String name, int page, String description) {
        this.name = name;
        this.page = page;
        this.description = description;
    }

    public Bookmark() {}

    public int getBookmark_id() {
        return bookmark_id;
    }

    public void setBookmark_id(int bookmark_id) {
        this.bookmark_id = bookmark_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
