package ru.korotaev.libraryapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b.book_id FROM Book b WHERE b.author_id = :authorId")
    List<Integer> findByAuthorId(@Param("authorId") int author_id);

    List<Book> findByName(String name);
}
