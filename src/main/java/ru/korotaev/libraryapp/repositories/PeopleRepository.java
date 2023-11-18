package ru.korotaev.libraryapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);

    List<User> findByName(String name);

    Page<User> findAll(Pageable pageable);

    List<User> findAll(Sort sort);

    List<User> findByNameContaining(String search);

    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String authorName, Pageable pageable);

    List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String authorName);
}
