package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.dao.BookDAO;
import ru.korotaev.libraryapp.dao.PersonDAO;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{isbn}")
    public String show(@PathVariable("isbn") int isbn, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(isbn));
        Optional<Person> bookOwner = bookDAO.getBookOwner(isbn);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        }else {
            model.addAttribute("people", personDAO.index());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{isbn}/edit")
    public String edit(Model model, @PathVariable("isbn") int isbn){
        model.addAttribute("book", bookDAO.show(isbn));
        return "books/edit";
    }

    @PatchMapping("/{isbn}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("isbn") int isbn){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.update(isbn, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{isbn}")
    public String delete(@PathVariable("isbn") int isbn){
        bookDAO.delete(isbn);
        return "redirect:/books";
    }

    @PatchMapping("/{isbn}/release")
    public String release(@PathVariable("isbn") int isbn){
        bookDAO.release(isbn);
        return "redirect:/books/" + isbn;
    }

    @PatchMapping("/{isbn}/assign")
    public String assign(@PathVariable("isbn") int isbn, @ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(isbn, selectedPerson);
        return "redirect:/books/" + isbn;
    }
}
