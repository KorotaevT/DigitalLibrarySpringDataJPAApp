package ru.korotaev.libraryapp.repositories.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;
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

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public List<Author> findAll(Sort sort) {
        return authorRepository.findAll(sort);
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

    public Page<Author> searchAuthors(String query, Pageable pageable) {
        return authorRepository.findByNameContainingIgnoreCaseOrBiographyContainingIgnoreCase(query, query, pageable);
    }

    public List<Author> searchAuthors(String query) {
            return authorRepository.findByNameContainingIgnoreCaseOrBiographyContainingIgnoreCase(query, query);
    }
}
