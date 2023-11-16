package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.dao.CalculatePageNumbers;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.Bookmark;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.services.AuthorService;
import ru.korotaev.libraryapp.repositories.services.BookmarkService;
import ru.korotaev.libraryapp.repositories.services.BooksService;
import ru.korotaev.libraryapp.repositories.services.PeopleService;
import ru.korotaev.libraryapp.util.BooksValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final AuthorService authorService;
    private final PeopleService peopleService;

    private final BookmarkService bookmarkService;
    private final BooksValidator booksValidator;

    @Autowired
    public BooksController(BooksService peopleService, BooksValidator booksValidator, AuthorService authorService, PeopleService peopleService1, BookmarkService bookmarkService) {
        this.booksService = peopleService;
        this.booksValidator = booksValidator;
        this.authorService = authorService;
        this.peopleService = peopleService1;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int pageSize = 10;
        Page<Book> bookPage = booksService.findAll(PageRequest.of(page, pageSize, Sort.by("name")));
        List<Integer> pageNumbers = CalculatePageNumbers.calculatePageNumbers(page, bookPage.getTotalPages());
        int booksCount = booksService.findAll().size();
        model.addAttribute("booksCount", booksCount);
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Book book = booksService.findOne(id);
        Hibernate.initialize(book.getUsers());
        Hibernate.initialize(book.getBookmarks());
        List<User> bookUsers = book.getUsers();
        List<Bookmark> bookmarks = book.getBookmarks();
        model.addAttribute("book", book);
        model.addAttribute("users", bookUsers);
        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("allUsers", peopleService.findAll(Sort.by("name")));
        model.addAttribute("newUser", new User());
        model.addAttribute("newBookmark", new Bookmark());
        model.addAttribute("deleteBookmark", new Bookmark());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model, @ModelAttribute("book") Book book){
        model.addAttribute("authors", authorService.findAll(Sort.by("name")));
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        booksValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            List<Author> authors = authorService.findAll(Sort.by("name"));
            model.addAttribute("authors", authors);
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/addUser")
    public String addUserToBook(@PathVariable("id") int id, @ModelAttribute("newUser") User newUser) {
        booksService.addUserToBook(id, newUser);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/unpinUser")
    public String deleteUserFromBook(@PathVariable("id") int id, @ModelAttribute("unpinUser") User unpinUser) {
        booksService.deleteUserFromBook(id, unpinUser);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/addBookmark")
    public String addBookmarkToBook(@PathVariable("id") int id, @ModelAttribute("newBookmark") Bookmark newBookmark) {
        booksService.addBookmarkToBook(id, newBookmark);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/deleteBookmark")
    public String deleteBookmarkFromBook(@PathVariable("id") int id, @ModelAttribute("deleteBookmark") Bookmark deleteBookmark, Model model) {
        booksService.deleteBookmarkFromBook(id, deleteBookmark);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("authors", authorService.findAll(Sort.by("name")));
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        book.setBook_id(id);
        booksValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignUsers(@PathVariable("id") int id, @ModelAttribute("author") Author author){
        booksService.findOne(id).setAuthor(author);
        return "redirect:/books/" + id;
    }
}
