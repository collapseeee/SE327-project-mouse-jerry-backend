package com.mousejerry.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "valid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenBlacklistService.isBlacklisted(token)).thenReturn(false);
        when(jwtUtils.validateToken(token)).thenReturn(true);
        when(jwtUtils.getUsernameFromToken(token)).thenReturn("john");

        UserDetails userDetails = new User("john", "pass", Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        when(userDetailsService.loadUserByUsername("john")).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("john", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NonBearerHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Basic 123456");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_BlacklistedToken() throws ServletException, IOException {
        String token = "blacklisted.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenBlacklistService.isBlacklisted(token)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        String token = "invalid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenBlacklistService.isBlacklisted(token)).thenReturn(false);
        when(jwtUtils.validateToken(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_ExceptionHandled() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenThrow(new RuntimeException("Request error"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
