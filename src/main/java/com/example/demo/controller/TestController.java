package com.example.demo.controller;

import com.example.demo.service.FooService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private FooService fooService;
    private RsaVerifier rsaVerifier;

    public TestController(FooService fooService, RsaVerifier rsaVerifier) {
        this.fooService = fooService;
        this.rsaVerifier = rsaVerifier;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> foo() {
        fooService.foo("1", "2");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/decode")
    public String decode(String token) {
        final Jwt decode = JwtHelper.decodeAndVerify(token, rsaVerifier);
        return decode.getClaims();
    }
}
