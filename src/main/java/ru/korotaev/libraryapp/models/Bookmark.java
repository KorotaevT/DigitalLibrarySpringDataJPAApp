package ru.korotaev.libraryapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "User id should not be empty")
    @Column(name = "user_id")
    private int user_id;

    @NotEmpty(message = "Book id should not be empty")
    @Column(name = "book_id")
    private int book_id;

    @NotEmpty(message = "Page should not be empty")
    @Column(name = "page")
    private int page;
    @Column(name = "desription")
    private String desription;

    public Bookmark(String name, int user_id, int book_id, int page, String desription) {
        this.name = name;
        this.user_id = user_id;
        this.book_id = book_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
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
}
