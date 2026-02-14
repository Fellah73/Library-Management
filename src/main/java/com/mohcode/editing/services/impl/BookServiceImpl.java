package com.mohcode.editing.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mohcode.editing.domain.entities.BookEntity;
import com.mohcode.editing.repositories.BookRepository;
import com.mohcode.editing.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    public BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // check if book exists @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    // create book
    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    // list all books
    @Override
    public List<BookEntity> findAllBooks() {
        return StreamSupport
                .stream(
                        bookRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    // list all books with pagination
    @Override
    public Page<BookEntity> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // find one book
    @Override
    public Optional<BookEntity> findOneBook(String isbn) {
        return bookRepository.findById(isbn);
    }

    // patch update book
    @Override
    public BookEntity partialUpdateBook(String isbn, BookEntity bookEntity) {
        return bookRepository.findById(isbn)
                .map(existingBook -> {
                    Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
                    Optional.ofNullable(bookEntity.getAuthor()).ifPresent(existingBook::setAuthor);
                    return bookRepository.save(existingBook);
                }).orElseThrow(() -> new RuntimeException("Book not found with isbn: " + isbn));
    }

    // delete book
    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

}
