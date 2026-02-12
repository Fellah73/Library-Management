package com.mohcode.editing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditingController {

    @RequestMapping("/hello")
    public String Hello() {
        return "Hello from the EditingController!";
    }
}
