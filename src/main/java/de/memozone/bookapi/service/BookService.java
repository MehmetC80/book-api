package de.memozone.bookapi.service;

import de.memozone.bookapi.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book create(Book book);

    Optional<Book> findById(String isbn);

    List<Book> listBooks();

}
