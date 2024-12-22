package com.raineri.security.jwt.config;

import com.raineri.security.jwt.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * WebSecurityConfig is a configuration class that sets up the security filter chain for the application.
 * It disables CSRF protection, permits all requests to endpoints under "/auth/**", and requires authentication
 * for all other requests. It also configures session management to be stateless and adds a JWT authentication filter
 * before the UsernamePasswordAuthenticationFilter.
 * 
 * This class uses the following components:
 * - AuthenticationProvider: A custom authentication provider for handling authentication logic.
 * - JwtAuthenticationFilter: A filter for processing JWT authentication tokens.
 * 
 * Annotations:
 * - @Configuration: Indicates that this class is a configuration class.
 * - @EnableWebSecurity: Enables Spring Security's web security support.
 * - @RequiredArgsConstructor: Generates a constructor with required arguments (final fields).
 * 
 * Methods:
 * - securityFilterChain(HttpSecurity http): Configures the security filter chain.
 * 
 * @param http the HttpSecurity to modify
 * @return the configured SecurityFilterChain
 * @throws Exception if an error occurs while configuring the security filter chain
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf
                        -> csrf.disable())
                .authorizeHttpRequests(authRequest
                        -> authRequest
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManager
                        -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
