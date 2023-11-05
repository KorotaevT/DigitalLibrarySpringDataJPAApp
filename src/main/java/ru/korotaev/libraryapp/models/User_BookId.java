package ru.korotaev.libraryapp.models;

import java.io.Serializable;
import java.util.Objects;

public class User_BookId implements Serializable {
    private int user_id;
    private int book_id;

    public User_BookId() {
    }

    public User_BookId(int user_id, int book_id) {
        this.user_id = user_id;
        this.book_id = book_id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User_BookId that = (User_BookId) o;
        return user_id == that.user_id && book_id == that.book_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, book_id);
    }
}
