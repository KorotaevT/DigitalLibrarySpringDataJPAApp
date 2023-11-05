package ru.korotaev.libraryapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.Book;
import ru.korotaev.libraryapp.models.User;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b.author_id FROM Book b WHERE b.author_id = :authorId")
    List<Book> findByAuthor_id(String author_id);

    List<Book> findByName(String name);
}
