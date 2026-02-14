package com.mohcode.editing.services;

import java.util.List;
import java.util.Optional;

import com.mohcode.editing.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);

    AuthorEntity updateAuthor(AuthorEntity author);

    List<AuthorEntity> findAll();

    boolean isExists(Long id);

    Optional<AuthorEntity> findById(Long id);

    AuthorEntity partialUpdateAuthor(AuthorEntity author);

    void deleteAuthor(Long id);
}
