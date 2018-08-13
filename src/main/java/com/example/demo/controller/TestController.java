package com.example.demo.controller;

import com.example.demo.service.FooService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private FooService fooService;

    public TestController(FooService fooService) {
        this.fooService = fooService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> foo() {
        fooService.foo("1", "2");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
