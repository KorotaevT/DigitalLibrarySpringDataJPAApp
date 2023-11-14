package ru.korotaev.libraryapp.repositories.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.BooksRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final AuthorService authorService;

    @Autowired
    public BooksService(BooksRepository booksRepository, AuthorService authorService) {
        this.booksRepository = booksRepository;
        this.authorService = authorService;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public Page<Book> findAll(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    public List<Book> findAll(Sort sort) {
        return booksRepository.findAll(sort);
    }

    public Book findOne(int id){
        Optional<Book> foundBook =  booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setBook_id(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Book validateNameAndAuthor(Book book) {
        List<Book> bookWithName = booksRepository.findByName(book.getName());
        List<Book> booksByAuthor = authorService.getAuthorBooks(book.getAuthor().getAuthor_id());

        return bookWithName.stream()
                .filter(booksByAuthor::contains)
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsersByBook(int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        if (book != null) {
            Hibernate.initialize(book.getUsers());
            return book.getUsers();
        }
        return Collections.emptyList();
    }

}
