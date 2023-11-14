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
import ru.korotaev.libraryapp.dao.PersonDAO;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.services.BooksService;
import ru.korotaev.libraryapp.repositories.services.PeopleService;
import ru.korotaev.libraryapp.util.UserValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final UserValidator userValidator;

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, UserValidator userValidator, PersonDAO personDAO) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.userValidator = userValidator;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        int pageSize = 10;
        Page<User> userPage = peopleService.findAll(PageRequest.of(page, pageSize, Sort.by("name")));
        List<Integer> pageNumbers = CalculatePageNumbers.calculatePageNumbers(page, userPage.getTotalPages());
        model.addAttribute("people", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        User user = peopleService.findOne(id);
        List<Book> userBooks = peopleService.getAllBooksByUser(id);
        List<Book> allBooks = booksService.findAll(Sort.by("name"));

        model.addAttribute("user", user);
        model.addAttribute("books", userBooks);
        model.addAttribute("allBooks", allBooks);
        model.addAttribute("newBook", new Book());
        model.addAttribute("unpinBook", new Book());
        return "people/show";
    }


    @PostMapping("/{id}/addBook")
    public String addBookToUser(@PathVariable("id") int id, @ModelAttribute("newBook") Book newBook) {
        peopleService.addBookToUser(id, newBook);
        return "redirect:/people/" + id;
    }

    @PostMapping("/{id}/unpinBook")
    public String deleteBookFromUser(@PathVariable("id") int id, @ModelAttribute("unpinBook") Book unpinBook) {
        peopleService.deleteBookFromUser(id, unpinBook);
        return "redirect:/people/" + id;
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(user);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id){
        user.setId(id);
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id, user);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
