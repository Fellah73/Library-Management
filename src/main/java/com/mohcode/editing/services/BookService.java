package com.mohcode.editing.services;

import java.util.List;

import com.mohcode.editing.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAllBooks();
}
