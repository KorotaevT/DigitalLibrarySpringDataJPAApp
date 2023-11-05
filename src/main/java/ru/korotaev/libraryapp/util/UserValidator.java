package ru.korotaev.libraryapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.services.PeopleService;

@Component
public class UserValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public UserValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (peopleService.validateEmail(user.getEmail())!=null && !peopleService.validateEmail(user.getEmail()).equals(user)){
            errors.rejectValue("email", "", "Эта почта уже занята");
        }
        if (peopleService.validateName(user.getName())!=null && !peopleService.validateName(user.getName()).equals(user)){
            errors.rejectValue("name", "", "Этот логин уже занят");
        }
    }
}