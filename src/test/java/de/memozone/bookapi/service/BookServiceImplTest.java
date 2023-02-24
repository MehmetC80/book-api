package de.memozone.bookapi.service;

import de.memozone.bookapi.entity.Book;
import de.memozone.bookapi.entity.BookEntity;
import de.memozone.bookapi.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.memozone.bookapi.TestData.testBook;
import static de.memozone.bookapi.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {


    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {

//        final Book book = Book.builder()
//                .isbn("02345678")
//                .author("Virgina Woolf")
//                .title("The Waves")
//                .build();

        final Book book = testBook();

//        final BookEntity bookEntity = BookEntity.builder()
//                .isbn("02345678")
//                .author("Virgina Woolf")
//                .title("The Waves")
//                .build();

        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);
        final Book result = underTest.save(book);

        assertEquals(book, result);
    }


    @Test
    public void testThatFindByIdReturnEmptyWhenNoBook() {

        final String isbn = "123123123";
        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());
        final Optional<Book> result = underTest.findById(isbn);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists(){
       final Book book = testBook();
       final BookEntity bookEntity= testBookEntity();
        when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));
        final Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(Optional.of(book), result);

    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExists(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        final List<Book> result = underTest.listBooks();
        assertEquals(0,result.size());
    }

    @Test
    public void testListBooksReturnsListOdBooksWhenBooksExists(){
        final BookEntity bookEntity = testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        assertEquals(1,result.size());
    }


    @Test
    public void testBookExistsReturnsFalseWhenBookDoesntExist(){

   when(bookRepository.existsById(any())).thenReturn(false);
   final boolean result = underTest.isBookExixts(testBook());
   assertEquals(false,result);
    }

    @Test
    public void testBookExistsReturnsTrueWhenBookDoesExist(){

        when(bookRepository.existsById(any())).thenReturn(true);
        final boolean result = underTest.isBookExixts(testBook());
        assertEquals(true,result);
    }


}