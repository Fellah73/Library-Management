package com.mohcode.editing.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    // create or update Book
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        boolean IsBookUpdated = bookService.isExists(isbn);
        BookEntity createdBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto createdBookDto = bookMapper.mapTo(createdBookEntity);
        if (IsBookUpdated) {
            return new ResponseEntity<>(createdBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
        }
    }

    // partial update Book
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(book);
        bookEntity.setIsbn(isbn);

        BookEntity createdBookEntity = bookService.partialUpdateBook(isbn, bookEntity);
        BookDto createdBookDto = bookMapper.mapTo(createdBookEntity);
        return new ResponseEntity<>(createdBookDto, HttpStatus.OK);
    }

    // find one Book
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> findBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBookEntity = bookService.findOneBook(isbn);
        return foundBookEntity.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // delete Book
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        System.err.println("Deleting book with isbn: " + isbn);
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // find all Books
    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable) {
        Page<BookEntity> bookEntities = bookService.findAllBooks(pageable);
        return bookEntities.map(bookMapper::mapTo);
    }

}
