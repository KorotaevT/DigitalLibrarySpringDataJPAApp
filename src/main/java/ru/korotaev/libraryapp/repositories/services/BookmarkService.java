package ru.korotaev.libraryapp.repositories.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.libraryapp.models.Bookmark;
import ru.korotaev.libraryapp.models.User;
import ru.korotaev.libraryapp.repositories.BookmarkRepository;
import ru.korotaev.libraryapp.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<Bookmark> findAll(){
        return bookmarkRepository.findAll();
    }

    public Bookmark findOne(int id){
        Optional<Bookmark> foundBookmark =  bookmarkRepository.findById(id);
        return foundBookmark.orElse(null);
    }

    @Transactional
    public void save(Bookmark bookmark){
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void update(int id, Bookmark updatedBookmark){
        updatedBookmark.setBookmark_id(id);
        bookmarkRepository.save(updatedBookmark);
    }

    @Transactional
    public void delete(int id){
        bookmarkRepository.deleteById(id);
    }
}
