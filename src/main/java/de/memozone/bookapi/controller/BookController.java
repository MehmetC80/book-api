package de.memozone.bookapi.controller;

import de.memozone.bookapi.entity.Book;
import de.memozone.bookapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(
            @PathVariable("isbn") final String isbn,
            @RequestBody final Book book) {

        book.setIsbn(isbn);

        final boolean isBookExists = bookService.isBookExixts(book);
        final Book savedBook = bookService.save(book);

        final ResponseEntity<Book> response;
        if(isBookExists) {
            response = new ResponseEntity<Book>(savedBook, HttpStatus.OK);
        } else {
            response = new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);

        }
        return response;

    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> getBookById(@PathVariable("isbn") final String isbn) {

        final Optional<Book> foundBook = bookService.findById(isbn);

        return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }


    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listOfBooks() {
        return new ResponseEntity<List<Book>>(bookService.listBooks(), HttpStatus.OK);
    }


}
