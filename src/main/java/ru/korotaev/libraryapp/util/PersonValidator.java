package ru.korotaev.libraryapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.libraryapp.dao.PersonDAO;
import ru.korotaev.libraryapp.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.getPersonByName(person.getName()).isPresent()
                && personDAO.getPersonBySurname(person.getSurname()).isPresent()
                && personDAO.getPersonByPatronymic(person.getPatronymic()).isPresent()){
            errors.rejectValue("name", "", "This full name is already taken");
            errors.rejectValue("surname", "", "This full surname is already taken");
            errors.rejectValue("patronymic", "", "This full patronymic is already taken");
        }
    }
}