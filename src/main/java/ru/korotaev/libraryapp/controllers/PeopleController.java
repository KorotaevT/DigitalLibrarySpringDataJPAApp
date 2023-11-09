package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.dao.PersonDAO;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.services.PeopleService;
import ru.korotaev.libraryapp.util.UserValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final UserValidator userValidator;

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PeopleService peopleService, UserValidator userValidator, PersonDAO personDAO) {
        this.peopleService = peopleService;
        this.userValidator = userValidator;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("user", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getAllBooksByUser(id));
        return "people/show";
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
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
