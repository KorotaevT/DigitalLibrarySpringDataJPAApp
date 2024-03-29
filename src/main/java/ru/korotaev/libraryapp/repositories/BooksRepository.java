package ru.korotaev.libraryapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByName(String name);

    Page<Book> findAll(Pageable pageable);

    List<Book> findAll(Sort sort);

    Page<Book> findByNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(String name, String authorName, Pageable pageable);

    List<Book> findByNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(String name, String authorName);
}
