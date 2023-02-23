package de.memozone.bookapi;

import de.memozone.bookapi.entity.Book;
import de.memozone.bookapi.entity.BookEntity;

public class TestData {


    private TestData(){}


    public static Book testBook() {
        return Book.builder()
                .isbn("02345678")
                .author("Virgina Woolf")
                .title("The Waves")
                .build();
    }

    public static BookEntity testBookEntity() {
        return BookEntity.builder()
                .isbn("02345678")
                .author("Virgina Woolf")
                .title("The Waves")
                .build();
    }




}
