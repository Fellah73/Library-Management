package com.mohcode.editing.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.mohcode.editing.domain.entities.AuthorEntity;
import com.mohcode.editing.repositories.AuthorRepository;
import com.mohcode.editing.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    public AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    // create Author
    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    // find all Authors
    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    // find one Author
    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorRepository.findById(id);
    }

    // update Author
    @Override
    public AuthorEntity updateAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    // partial update Author
    @Override
    public AuthorEntity partialUpdateAuthor(AuthorEntity authorEntity) {

        return authorRepository.findById(authorEntity.getId())
                .map(existingAuthor -> {
                    Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
                    Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
                    return authorRepository.save(existingAuthor);
                })
                .orElseThrow(() -> new RuntimeException("Author with ID " + authorEntity.getId() + " not found"));
    }

    // delete Author
    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
