package com.mohcode.editing.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mohcode.editing.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAllBooks();

    Page<BookEntity> findAllBooks(Pageable pageable);

    Optional<BookEntity> findOneBook(String isbn);

    boolean isExists(String isbn);

    BookEntity partialUpdateBook(String isbn, BookEntity book);

    void deleteBook(String isbn);
}
