package com.mohcode.editing.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mohcode.editing.domain.dto.AuthorDto;
import com.mohcode.editing.domain.entities.AuthorEntity;
import com.mohcode.editing.mappers.Mapper;
import com.mohcode.editing.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthorController {
    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity createdAuthorEntity = authorService.createAuthor(authorEntity);
        return authorMapper.mapTo(createdAuthorEntity);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> getAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

}
