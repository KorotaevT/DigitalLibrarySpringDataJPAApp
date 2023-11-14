package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.repositories.services.AuthorService;
import ru.korotaev.libraryapp.repositories.services.BooksService;
import ru.korotaev.libraryapp.util.AuthorValidator;
import ru.korotaev.libraryapp.util.BooksValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    private final BooksService booksService;

    private final BooksValidator booksValidator;
    private final AuthorValidator authorValidator;

    @Autowired
    public AuthorController(AuthorService authorService, BooksService booksService, BooksValidator booksValidator, AuthorValidator authorValidator) {
        this.authorService = authorService;
        this.booksService = booksService;
        this.booksValidator = booksValidator;
        this.authorValidator = authorValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("authors", authorService.findAll()
                .stream()
                .sorted(Comparator.comparing(Author::getName))
                .collect(Collectors.toList()));
        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("author", authorService.findOne(id));
        model.addAttribute("books", authorService.getAuthorBooks(id));
        return "authors/show";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("author") Author author){
        return "authors/new";
    }

    @GetMapping("/{id}/new")
    public String newBookWithAuthor(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book){
        model.addAttribute("author", authorService.findOne(id));
        model.addAttribute("book", new Book());
        return "books/newWithAuthor";
    }

    @PostMapping()
    public String create(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult){
        authorValidator.validate(author, bindingResult);
        if (bindingResult.hasErrors()){
            return "authors/new";
        }
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("author", authorService.findOne(id));
        return "authors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("author") Author author, BindingResult bindingResult, @PathVariable("id") int id){
        author.setAuthor_id(id);
        authorValidator.validate(author, bindingResult);
        if(bindingResult.hasErrors()){
            return "authors/edit";
        }
        authorService.update(id, author);
        return "redirect:/authors/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        authorService.delete(id);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/newBook")
    public String createNewBook(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int id, BindingResult bindingResult, Model model){
        booksValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            List<Author> authors = authorService.findAll();
            model.addAttribute("authors", authors);
            return "books/newWithAuthor";
        }
        booksService.save(book);
        return "redirect:/authors/" + id;
    }

    @GetMapping("/{id}/bookEdit/{bookId}")
    public String editBook(Model model, @PathVariable("id") int id, @PathVariable("bookId") int bookId){
        model.addAttribute("authors", authorService.findAll()
                .stream()
                .sorted(Comparator.comparing(Author::getName))
                .collect(Collectors.toList()));
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/bookUpdate")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        book.setBook_id(id);
        booksValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/bookDelete")
    public String deleteBook(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}
