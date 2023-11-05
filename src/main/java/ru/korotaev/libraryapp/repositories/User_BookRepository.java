package ru.korotaev.libraryapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.User_Book;

import java.util.List;

@Repository
public interface User_BookRepository extends JpaRepository<User_Book, Integer> {
    @Query("SELECT ub.book_id FROM User_Book ub WHERE ub.user_id = :userId")
    List<Integer> findBookIdsByUserId(@Param("userId") int userId);

    @Query("SELECT ub.user_id FROM User_Book ub WHERE ub.book_id = :bookId")
    List<Integer> findUserIdsByBookId(@Param("bookId") int bookId);
}

