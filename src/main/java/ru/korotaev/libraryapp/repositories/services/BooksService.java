package ru.korotaev.libraryapp.repositories.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.Bookmark;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.BookmarkRepository;
import ru.korotaev.libraryapp.repositories.BooksRepository;
import ru.korotaev.libraryapp.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    private final PeopleRepository peopleRepository;

    private final BookmarkRepository bookmarkRepository;
    private final AuthorService authorService;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository, BookmarkRepository bookmarkRepository, AuthorService authorService) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
        this.bookmarkRepository = bookmarkRepository;
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

    @Transactional
    public void addUserToBook(int bookId, User newUser) {
        Book book = booksRepository.findById(bookId).orElseThrow();
        User savedUser = peopleRepository.findById(newUser.getId()).orElseThrow();
        Hibernate.initialize(book.getUsers());
        Hibernate.initialize(savedUser.getBooks());
        if(!book.getUsers().contains(savedUser)) {
            book.getUsers().add(savedUser);
            savedUser.getBooks().add(book);

            booksRepository.save(book);
            peopleRepository.save(savedUser);
        }
    }

    @Transactional
    public void deleteUserFromBook(int bookId, User unpinUser) {
        Book book = booksRepository.findById(bookId).orElseThrow();
        User savedUser = peopleRepository.findById(unpinUser.getId()).orElseThrow();
        Hibernate.initialize(book.getUsers());
        Hibernate.initialize(savedUser.getBooks());
        if(book.getUsers().contains(savedUser)) {
            book.getUsers().remove(savedUser);
            savedUser.getBooks().remove(book);
            booksRepository.save(book);
            peopleRepository.save(savedUser);
        }
    }

    @Transactional
    public void addBookmarkToBook(int bookId, Bookmark newBookmark) {
        Book book = booksRepository.findById(bookId).orElseThrow();
        if(!book.getBookmarks().contains(newBookmark)) {
            book.getBookmarks().add(newBookmark);
            newBookmark.setBook(book);;

            booksRepository.save(book);
            bookmarkRepository.save(newBookmark);
        }
    }

    @Transactional
    public void deleteBookmarkFromBook(int bookId, Bookmark deleteBookmark) {
        Book book = booksRepository.findById(bookId).orElseThrow();
        Bookmark savedBookmark = bookmarkRepository.findById(deleteBookmark.getBookmark_id()).orElseThrow();
        if(book.getBookmarks().contains(savedBookmark)) {
            book.getBookmarks().remove(savedBookmark);
            booksRepository.save(book);
            bookmarkRepository.deleteById(savedBookmark.getBookmark_id());
        }
    }

    public Page<Book> searchBooks(String query, Pageable pageable) {
        return booksRepository.findByNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(query, query, pageable);
    }

    public List<Book> searchBooks(String query) {
        return booksRepository.findByNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(query, query);
    }

}
