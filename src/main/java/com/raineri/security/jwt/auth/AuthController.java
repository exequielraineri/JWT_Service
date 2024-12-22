package com.raineri.security.jwt.auth;

import com.raineri.security.jwt.service.AuthService;
import com.raineri.security.jwt.dto.LoginRequest;
import com.raineri.security.jwt.dto.RegisterRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController handles authentication-related requests such as login and registration.
 * It provides endpoints for user authentication and registration.
 * 
 * Endpoints:
 * - POST /auth/login: Authenticates a user with the provided credentials.
 * - POST /auth/register: Registers a new user with the provided details.
 * 
 * Dependencies:
 * - AuthService: Service layer for handling authentication logic.
 * 
 * Methods:
 * - login(LoginRequest request): Authenticates a user and returns a response with authentication details.
 * - register(RegisterRequest request): Registers a new user and returns a response with registration details.
 * 
 * Exception Handling:
 * - Catches any exceptions during the execution of login and register methods and returns an internal server error response with the exception message.
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
