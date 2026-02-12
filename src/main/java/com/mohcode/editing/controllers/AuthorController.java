package com.mohcode.editing.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthorController {
    
    @PostMapping(path = "/authors")
    public String postMethodName(@RequestBody String entity) {
        
        return entity;
    }
    
}
