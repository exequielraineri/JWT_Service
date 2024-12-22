/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raineri.security.jwt.service;

import com.raineri.security.jwt.dto.AuthResponse;
import com.raineri.security.jwt.dto.LoginRequest;
import com.raineri.security.jwt.dto.RegisterRequest;
import com.raineri.security.jwt.entity.EnumRole;
import com.raineri.security.jwt.entity.UserEntity;
import com.raineri.security.jwt.jwt.JwtService;
import com.raineri.security.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService is a service class that handles authentication and registration
 * of users.
 * It uses JwtService to generate JWT tokens, UserRepository to interact with
 * the user database,
 * AuthenticationManager to authenticate users, and PasswordEncoder to encode
 * user passwords.
 * 
 * The class provides two main methods:
 * 
 * - login(LoginRequest request): Authenticates a user with the provided
 * username and password,
 * and returns an AuthResponse containing a JWT token and user ID.
 * 
 * - register(RegisterRequest request): Registers a new user with the provided
 * details,
 * saves the user to the database, and returns an AuthResponse containing a JWT
 * token.
 * 
 * Dependencies are injected using Spring's @Autowired annotation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

        @Autowired
        private JwtService jwtService;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private PasswordEncoder passwordEncoder;

        public AuthResponse login(LoginRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                request.getPassword()));

                UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return AuthResponse.builder()
                                .token(jwtService.getToken(userEntity))
                                .userID(userEntity.getId())
                                .build();
        }

        public AuthResponse register(RegisterRequest request) {
                UserEntity user = UserEntity.builder()
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .country(request.getCountry())
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .role(EnumRole.USER)
                                .build();

                userRepository.save(user);
                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }
}
