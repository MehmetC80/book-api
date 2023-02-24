package de.memozone.bookapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.memozone.bookapi.TestData;
import de.memozone.bookapi.entity.Book;
import de.memozone.bookapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static de.memozone.bookapi.TestData.testBook;
import static de.memozone.bookapi.TestData.testBookEntity;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreatedReturnsHttp200() throws Exception {
        final Book book = testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));

    }

    @Test
    public void testThatBookIsUpdatedReturnsHttp201() throws Exception {
        final Book book = testBook();

        bookService.save(book);

        book.setTitle("The Lord of the Rings");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));

    }


    @Test
    public void testThatGetBook404WhenBookNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/123123123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatGetBookReturnsHttp200WhenExits() throws Exception {

        final Book book = TestData.testBook();
        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }


    @Test
    public void testThatListBooksReturnsHttp200EmptyListWhenNoBooksExists() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    public void testThatListBooksReturnsHttp200EmptyListWhenBooksExists() throws Exception {

        final Book book = TestData.testBook();
        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book.getAuthor()));


    }

}