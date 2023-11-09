package ru.korotaev.libraryapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.services.PeopleService;

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
        int userId = user.getId();
        User existingUserName = peopleService.validateName(user.getName());
        User existingUserEmail = peopleService.validateEmail(user.getEmail());
        if (existingUserName != null && existingUserName.getId() != userId) {
            errors.rejectValue("name", "", "Этот логин уже существует");
        }
        if (existingUserEmail != null && existingUserEmail.getId() != userId) {
            errors.rejectValue("email", "", "Этот email уже существует");
        }
    }
}