package ru.korotaev.libraryapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.services.AuthorService;
import ru.korotaev.libraryapp.services.BooksService;

@Component
public class BooksValidator implements Validator {

    private final BooksService booksService;
    private final AuthorService authorService;

    @Autowired
    public BooksValidator(BooksService booksService, AuthorService authorService) {
        this.booksService = booksService;
        this.authorService = authorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (booksService.validateNameAndAuthor(book.getName(), authorService.findOne(book.getBook_id()).getName())!=null && !(booksService.validateNameAndAuthor(book.getName(), authorService.findOne(book.getBook_id()).getName()).equals(book))){
            errors.rejectValue("nameAndAuthor", "", "Эта книга уже существует");
        }
    }
}