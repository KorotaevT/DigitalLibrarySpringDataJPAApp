package ru.korotaev.libraryapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.services.AuthorService;

@Component
public class AuthorValidator implements Validator {

    private final AuthorService authorService;

    @Autowired
    public AuthorValidator(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;
        int authorId = author.getAuthor_id();
        Author existingAuthor = authorService.validateName(author.getName());
        if (existingAuthor != null && existingAuthor.getAuthor_id() != authorId) {
            errors.rejectValue("name", "", "Этот автор уже существует");
        }
    }

}