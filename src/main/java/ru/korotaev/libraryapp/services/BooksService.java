package ru.korotaev.libraryapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.repositories.AuthorRepository;
import ru.korotaev.libraryapp.repositories.BooksRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, AuthorRepository authorRepository) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
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

    public Book validateNameAndAuthor(String name, int author) {
        List<Book> bookWithName = booksRepository.findByName(name);
        List<Book> booksByAuthor = findByAuthorId(author);

        return bookWithName.stream()
                .filter(booksByAuthor::contains)
                .findFirst()
                .orElse(null);
    }


    public List<Book> findByAuthorId(int authorId){
        List<Integer> bookIdsWithAuthor = booksRepository.findByAuthorId(authorId);
        List<Book> bookWithAuthor = bookIdsWithAuthor.stream()
                .map(id -> booksRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return bookWithAuthor;
    }

    public String findAuthorNameByBookId(int bookId) {
        Optional<Book> bookOptional = booksRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            int authorId = book.getAuthor_id();
            Optional<Author> authorOptional = authorRepository.findById(authorId);
            if (authorOptional.isPresent()) {
                Author author = authorOptional.get();
                return author.getName();
            }
        }
        return null;
    }

}
