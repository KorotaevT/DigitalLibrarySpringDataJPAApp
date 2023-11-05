package ru.korotaev.libraryapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.models.User_Book;
import ru.korotaev.libraryapp.repositories.BooksRepository;
import ru.korotaev.libraryapp.repositories.PeopleRepository;
import ru.korotaev.libraryapp.repositories.User_BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class User_BookService {

    private final User_BookRepository userBookRepository;
    private final BooksRepository bookRepository;

    private final PeopleRepository peopleRepository;

    @Autowired
    public User_BookService(User_BookRepository userBookRepository, BooksRepository bookRepository, PeopleRepository peopleRepository) {
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<User_Book> findAll(){
        return userBookRepository.findAll();
    }

    public User_Book findOne(int id){
        Optional<User_Book> foundUserBook =  userBookRepository.findById(id);
        return foundUserBook.orElse(null);
    }

    @Transactional
    public void save(User_Book userBook){
        userBookRepository.save(userBook);
    }

    @Transactional
    public void update(int user_id, int book_id, User_Book updatedUserBook){
        updatedUserBook.setUser_id(user_id);
        updatedUserBook.setBook_id(book_id);
        userBookRepository.save(updatedUserBook);
    }

    @Transactional
    public void delete(int id){
        userBookRepository.deleteById(id);
    }

    public List<Book> findBooksByUserId(int userId) {
        List<Integer> bookIds = userBookRepository.findBookIdsByUserId(userId);
        List<Book> books = bookRepository.findAllById(bookIds);
        return books;
    }

    public List<User> findUsersByBookId(int bookId) {
        List<Integer> userIds = userBookRepository.findUserIdsByBookId(bookId);
        List<User> users = peopleRepository.findAllById(userIds);
        return users;
    }

}
