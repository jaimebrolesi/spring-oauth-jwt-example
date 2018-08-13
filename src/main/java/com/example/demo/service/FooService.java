package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    public void foo(String bar, String foo) {
        throw new RuntimeException("I'm an exception intercepted by the aspectJ");
    }
}
