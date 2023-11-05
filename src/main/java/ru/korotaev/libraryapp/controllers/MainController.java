package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.services.AuthorService;
import ru.korotaev.libraryapp.services.BooksService;
import ru.korotaev.libraryapp.util.AuthorValidator;

@Controller
public class MainController {

    @Autowired
    public MainController() {
    }

    @GetMapping("/")
    public String index(Model model){
        return "main/main";
    }
}
