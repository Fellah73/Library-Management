package com.mohcode.editing.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mohcode.editing.domain.dto.AuthorDto;
import com.mohcode.editing.domain.entities.AuthorEntity;
import com.mohcode.editing.mappers.Mapper;
import com.mohcode.editing.services.AuthorService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class AuthorController {
    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    // create Author
    @PostMapping(path = "/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity createdAuthorEntity = authorService.createAuthor(authorEntity);
        return authorMapper.mapTo(createdAuthorEntity);
    }

    // find all Authors
    @GetMapping(path = "/authors")
    public List<AuthorDto> getAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    // find one Author
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findById(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // update Author
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {

        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        authorEntity.setId(id);

        AuthorEntity updatedAuthorEntity = authorService.updateAuthor(authorEntity);
        AuthorDto updatedAuthorDto = authorMapper.mapTo(updatedAuthorEntity);
        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);
    }

    // partial update Author
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto) {

        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        authorEntity.setId(id);

        AuthorEntity updatedAuthorEntity = authorService.partialUpdateAuthor(authorEntity);
        AuthorDto updatedAuthorDto = authorMapper.mapTo(updatedAuthorEntity);
        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);
    }

    // delete author
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
