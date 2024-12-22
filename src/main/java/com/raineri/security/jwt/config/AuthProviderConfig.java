package com.raineri.security.jwt.config;

import com.raineri.security.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up authentication providers and related beans.
 * This class is annotated with @Configuration to indicate that it is a source of bean definitions.
 * It uses constructor-based dependency injection with the @RequiredArgsConstructor annotation.
 * 
 * Beans provided:
 * 
 * - AuthenticationManager: Retrieves the AuthenticationManager from the provided AuthenticationConfiguration.
 * - AuthenticationProvider: Configures a DaoAuthenticationProvider with a custom UserDetailsService and PasswordEncoder.
 * - UserDetailsService: Loads user-specific data during authentication by finding a user by username.
 * - PasswordEncoder: Encodes passwords using BCrypt hashing algorithm.
 * 
 * Dependencies:
 * 
 * - UserRepository: Repository for accessing user data.
 */
@Configuration
@RequiredArgsConstructor
public class AuthProviderConfig {

    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
