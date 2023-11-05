package ru.korotaev.libraryapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.BooksRepository;
import ru.korotaev.libraryapp.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
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

    public List<Book> validateNameAndAuthor(String name, String author) {
        List<Book> bookWithName = booksRepository.findByName(name);
        List<Book> bookWithAuthor = booksRepository.findByAuthor_id(author);
        List<Book> booksWithSameNameAndAuthor = new ArrayList<>();
        booksWithSameNameAndAuthor.addAll(bookWithName);
        booksWithSameNameAndAuthor.addAll(bookWithAuthor);
        if (booksWithSameNameAndAuthor.isEmpty()) {
            return null;
        }
        return booksWithSameNameAndAuthor;
    }

}
