package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.dao.PersonDAO;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.services.AuthorService;
import ru.korotaev.libraryapp.services.BooksService;
import ru.korotaev.libraryapp.util.BooksValidator;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final AuthorService authorService;
    private final BooksValidator booksValidator;

    @Autowired
    public BooksController(BooksService peopleService, BooksValidator booksValidator, PersonDAO personDAO, AuthorService authorService) {
        this.booksService = peopleService;
        this.booksValidator = booksValidator;
        this.authorService = authorService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("users", booksService.getAllUsersByBook(id));
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model, @ModelAttribute("book") Book book){
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        booksValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            List<Author> authors = authorService.findAll();
            model.addAttribute("authors", authors);
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("authors", authorService.findAll());
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
        return "redirect:/books";
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
