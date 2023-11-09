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

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 255, message = "Enter correct name")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Page should not be empty")
    @Column(name = "page")
    private int page;
    @Column(name = "desription")
    private String desription;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    @NotNull(message = "Book should not be empty")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @NotNull(message = "User should not be empty")
    private User user;

    public Bookmark(String name, int page, String desription) {
        this.name = name;
        this.page = page;
        this.desription = desription;
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

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
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
