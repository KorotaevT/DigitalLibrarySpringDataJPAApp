package ru.korotaev.libraryapp.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Enter correct name")
    private String name;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Enter correct surname")
    private String surname;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Enter correct patronymic")
    private String patronymic;

    @Min(value=1901, message = "year > 1900")
    private int year_of_birth;

    public Person(String name, String surname, String patronymic, int year_of_birth) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.year_of_birth = year_of_birth;
    }

    public Person() {}

    public int getId() {return id;}

    public void setId(int person_id) {
        this.id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
