package ru.korotaev.libraryapp.controllers;

import jakarta.validation.Valid;
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
    public String index(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "search", required = false) String search,
                        Model model) {

        int pageSize = 10;
        Page<Author> authorPage;
        int authorsCount = 0;
        if (search != null && !search.isEmpty()) {
            authorPage = authorService.searchAuthors(search, PageRequest.of(page, pageSize, Sort.by("name")));
            authorsCount = authorService.searchAuthors(search).size();
        } else {
            authorPage = authorService.findAll(PageRequest.of(page, pageSize, Sort.by("name")));
            authorsCount = authorService.findAll().size();
        }
        List<Integer> pageNumbers = CalculatePageNumbers.calculatePageNumbers(page, authorPage.getTotalPages());
        model.addAttribute("authors", authorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("authorsCount", authorsCount);
        model.addAttribute("totalPages", authorPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("searchParam", search);
        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("author", authorService.findOne(id));
        model.addAttribute("books", authorService.getAuthorBooks(id));
        model.addAttribute("deleteBook", new Book());
        return "authors/show";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("author") Author author){
        return "authors/new";
    }

    @GetMapping("/{id}/newBook")
    public String newBookWithAuthor(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book){
        model.addAttribute("author", authorService.findOne(id));
        return "authors/newWithAuthor";
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
    public String update(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, @PathVariable("id") int id){
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
    public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model, @PathVariable String id){
        booksValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            List<Author> authors = authorService.findAll(Sort.by("name"));
            model.addAttribute("authors", authors);
            model.addAttribute("author", authorService.findOne(Integer.parseInt(id)));
            return "authors/newWithAuthor";
        }
        booksService.save(book);
        return "redirect:/authors/" + id;
    }

    @PostMapping("/{id}/deleteBook")
    public String deleteBook(@PathVariable("id") int id, @ModelAttribute("deleteBook") Book deleteBook, Model model){
        booksService.delete(deleteBook.getBook_id());
        return "redirect:/authors/" + id;
    }
}
