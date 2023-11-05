package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.dao.PersonDAO;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.services.BooksService;
import ru.korotaev.libraryapp.services.User_BookService;
import ru.korotaev.libraryapp.util.BooksValidator;
import ru.korotaev.libraryapp.util.UserValidator;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final User_BookService userBookService;
    private final BooksValidator booksValidator;

    @Autowired
    public BooksController(BooksService peopleService, User_BookService userBookService, BooksValidator booksValidator, PersonDAO personDAO) {
        this.booksService = peopleService;
        this.userBookService = userBookService;
        this.booksValidator = booksValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("users", userBookService.findUsersByBookId(id));
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        booksValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        booksValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}
