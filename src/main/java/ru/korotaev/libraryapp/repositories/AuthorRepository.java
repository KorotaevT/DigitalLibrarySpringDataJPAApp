package ru.korotaev.libraryapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.Author;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByName(String name);

    Page<Author> findAll(Pageable pageable);

    List<Author> findAll(Sort sort);
}
