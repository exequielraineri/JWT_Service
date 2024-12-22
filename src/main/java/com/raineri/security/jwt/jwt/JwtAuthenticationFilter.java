package com.raineri.security.jwt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtAuthenticationFilter is a custom filter that extends OncePerRequestFilter.
 * It is responsible for processing JWT authentication tokens in the request headers.
 * 
 * This filter extracts the JWT token from the Authorization header, validates it,
 * and sets the authentication in the SecurityContext if the token is valid.
 * 
 * Dependencies:
 * - JwtService: Service for handling JWT operations such as extracting username and validating tokens.
 * - UserDetailsService: Service for loading user-specific data.
 * 
 * Methods:
 * - doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain):
 *   This method is overridden to implement the filtering logic. It extracts the token, validates it,
 *   and sets the authentication in the SecurityContext if the token is valid.
 * 
 * - getTokenFromRequest(HttpServletRequest request):
 *   This method extracts the JWT token from the Authorization header of the request.
 * 
 * Usage:
 * This filter should be registered in the security configuration to intercept incoming requests
 * and perform JWT authentication.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        String username;

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtService.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
