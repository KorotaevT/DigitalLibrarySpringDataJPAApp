package ru.korotaev.libraryapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korotaev.libraryapp.models.User;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
}
