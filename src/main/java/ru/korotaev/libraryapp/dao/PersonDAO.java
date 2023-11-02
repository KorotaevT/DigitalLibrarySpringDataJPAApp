package ru.korotaev.libraryapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Person> getPersonByName(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[] {name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> getPersonBySurname(String surname){
        return jdbcTemplate.query("SELECT * FROM Person WHERE surname=?", new Object[] {surname}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> getPersonByPatronymic(String patronymic){
        return jdbcTemplate.query("SELECT * FROM Person WHERE patronymic=?", new Object[] {patronymic}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person (name, surname, patronymic, year_of_birth) VALUES(?, ?, ?, ?)", person.getName(), person.getSurname(), person.getPatronymic(), person.getYear_of_birth());
    }

    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, surname=?, patronymic=?, year_of_birth=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getSurname(), updatedPerson.getPatronymic(), updatedPerson.getYear_of_birth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
