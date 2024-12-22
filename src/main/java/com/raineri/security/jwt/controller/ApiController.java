package com.raineri.security.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController is a REST controller class that provides an endpoint for a simple "Hello World" message.
 * @author Exequiel
 */
@RestController
@RequestMapping("/api/v1/")
public class ApiController {

    @GetMapping("hello")
    public ResponseEntity<?> hello(@RequestParam(defaultValue = "World") String name) {
        return ResponseEntity.ok("Hello " + name);
    }
}
