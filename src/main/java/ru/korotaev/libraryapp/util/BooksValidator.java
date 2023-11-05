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
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        int bookId = book.getBook_id();
        Book existingBookNameAndAuthor = booksService.validateNameAndAuthor(book.getName(), book.getAuthor_id());
        if (existingBookNameAndAuthor != null && existingBookNameAndAuthor.getBook_id() != bookId) {
            errors.rejectValue("name", "", "Эта книга уже существует");
            errors.rejectValue("author_id", "", "Эта книга уже существует");
        }
    }
}