package ru.korotaev.libraryapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.Bookmark;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.services.AuthorService;
import ru.korotaev.libraryapp.repositories.services.BookmarkService;
import ru.korotaev.libraryapp.repositories.services.BooksService;
import ru.korotaev.libraryapp.repositories.services.PeopleService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final AuthorService authorService;
    private final BookmarkService bookmarkService;

    @Autowired
    public SearchController(PeopleService peopleService, BooksService booksService, AuthorService authorService, BookmarkService bookmarkService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.authorService = authorService;
        this.bookmarkService = bookmarkService;
    }
}
