package com.mohcode.editing.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mohcode.editing.domain.dto.BookDto;
import com.mohcode.editing.domain.entities.BookEntity;
import com.mohcode.editing.mappers.Mapper;
import com.mohcode.editing.services.BookService;

@RestController
public class BookController {
    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity createdBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto createdBookDto = bookMapper.mapTo(createdBookEntity);
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAllBooks();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

}
