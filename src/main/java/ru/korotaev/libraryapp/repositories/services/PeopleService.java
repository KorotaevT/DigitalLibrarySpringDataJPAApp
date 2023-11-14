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
import ru.korotaev.libraryapp.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<User> findAll(){
        return peopleRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable) {
        return peopleRepository.findAll(pageable);
    }

    public List<User> findAll(Sort sort) {
        return peopleRepository.findAll(sort);
    }

    public User findOne(int id){
        Optional<User> foundPerson =  peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void addBookToUser(int userId, Book newBook) {
        User user = peopleRepository.findById(userId).orElseThrow();
        Book savedBook = booksRepository.findById(newBook.getBook_id()).orElseThrow();
        Hibernate.initialize(user.getBooks());
        Hibernate.initialize(savedBook.getUsers());
        if(!user.getBooks().contains(savedBook)) {
            user.getBooks().add(savedBook);
            savedBook.getUsers().add(user);

            peopleRepository.save(user);
            booksRepository.save(savedBook);
        }
    }

    @Transactional
    public void deleteBookFromUser(int userId, Book newBook) {
        User user = peopleRepository.findById(userId).orElseThrow();
        Book savedBook = booksRepository.findById(newBook.getBook_id()).orElseThrow();
        Hibernate.initialize(user.getBooks());
        Hibernate.initialize(savedBook.getUsers());
        if(user.getBooks().contains(savedBook)) {
            user.getBooks().remove(savedBook);
            savedBook.getUsers().remove(user);
            peopleRepository.save(user);
            booksRepository.save(savedBook);
        }
    }


    @Transactional
    public void save(User person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, User updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public User validateEmail(String email) {
        List<User> peopleWithEmail = peopleRepository.findByEmail(email);
        return  peopleWithEmail.size()==0?null:peopleWithEmail.get(0);
    }

    public User validateName(String name) {
        List<User> peopleWithName = peopleRepository.findByName(name);
        return  peopleWithName.size()==0?null:peopleWithName.get(0);
    }

    public List<Book> getAllBooksByUser(int userId) {
        User user = peopleRepository.findById(userId).orElse(null);
        if (user != null) {
            Hibernate.initialize(user.getBooks());
            return user.getBooks();
        }
        return Collections.emptyList();
    }

}
