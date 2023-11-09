package ru.korotaev.libraryapp.repositories.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public Author findOne(int id){
        Optional<Author> foundAuthor =  authorRepository.findById(id);
        return foundAuthor.orElse(null);
    }

    @Transactional
    public void save(Author author){
        authorRepository.save(author);
    }

    @Transactional
    public void update(int id, Author updatedAuthor){
        updatedAuthor.setAuthor_id(id);
        authorRepository.save(updatedAuthor);
    }

    @Transactional
    public void delete(int id){
        authorRepository.deleteById(id);
    }

    public Author validateName(String name) {
        List<Author> authorsWithName = authorRepository.findByName(name);
        return  authorsWithName.size()==0?null:authorsWithName.get(0);
    }

    @Transactional
    public List<Book> getAuthorBooks(int authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        if (author != null) {
            Hibernate.initialize(author.getBooks());
            return author.getBooks();
        }
        return null;
    }
}
