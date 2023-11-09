package ru.korotaev.libraryapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
