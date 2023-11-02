package ru.korotaev.libraryapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book show(int isbn){
        return jdbcTemplate.query("SELECT * FROM Book WHERE isbn=?", new Object[]{isbn}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(book, author, year) VALUES(?, ?, ?)", book.getBook(), book.getAuthor(), book.getYear());
    }

    public void update(int isbn, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET book=?, author=?, year=? WHERE isbn=?", updatedBook.getBook(),
                updatedBook.getAuthor(), updatedBook.getYear(), isbn);
    }

    public void delete(int isbn){
        jdbcTemplate.update("DELETE FROM Book WHERE isbn=?", isbn);
    }

    public Optional<Person> getBookOwner(int isbn){
        return jdbcTemplate.query("SELECT Person.* FROM Book Join Person ON Book.person_id = person.id " +
                "WHERE Book.isbn=?", new Object[]{isbn}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void release(int isbn){
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE isbn=?", isbn);
    }

    public void assign(int isbn, Person selectedPerson){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE isbn=?", selectedPerson.getId(), isbn);
    }


}
