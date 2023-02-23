package de.memozone.bookapi.service;

import de.memozone.bookapi.entity.Book;
import de.memozone.bookapi.entity.BookEntity;
import de.memozone.bookapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static de.memozone.bookapi.TestData.testBook;
import static de.memozone.bookapi.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.*;
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
        final Book result = underTest.create(book);

        assertEquals(book, result);
    }
}