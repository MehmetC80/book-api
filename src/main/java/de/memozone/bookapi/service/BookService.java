package de.memozone.bookapi.service;

import de.memozone.bookapi.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {


    Boolean isBookExixts(Book book);

    Book save(Book book);

    Optional<Book> findById(String isbn);

    List<Book> listBooks();

    void deleteBookById(String isbn);

}
