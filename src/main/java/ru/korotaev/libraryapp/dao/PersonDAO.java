package ru.korotaev.libraryapp.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Book;

import java.util.List;

@Component
public class PersonDAO {

    private final EntityManager entityManager;

    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Book> getBooksByPersonId(int id){
        Session session = entityManager.unwrap(Session.class);

//        List<Book> bookList = session.createQuery("SELECT b\n" +
//                        "FROM Book b\n" +
//                        "JOIN b.users u\n" +
//                        "WHERE u.id = :userId\n")
//                .setParameter("personId", id)
//                .getResultList();
        return null;

    }
}
