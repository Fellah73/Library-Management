package com.mohcode.editing.services;

import java.util.List;

import com.mohcode.editing.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);


    List<AuthorEntity> findAll();
}
