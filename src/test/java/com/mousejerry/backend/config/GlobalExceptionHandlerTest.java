package com.mousejerry.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleBadCredentialsException() {
        BadCredentialsException ex = new BadCredentialsException("Invalid creds");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleBadCredentialsException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid creds", response.getBody().get("error"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad input");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad input", response.getBody().get("error"));
    }

    @Test
    void testHandleNoSuchElementException() {
        java.util.NoSuchElementException ex = new java.util.NoSuchElementException("Not found");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleNoSuchElementException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().get("error"));
    }

    @Test
    void testHandleNoResourceFoundException() {
        org.springframework.web.servlet.resource.NoResourceFoundException ex =
                new org.springframework.web.servlet.resource.NoResourceFoundException(
                        org.springframework.http.HttpMethod.GET,
                        "api/v1/admin/orders",
                        "No static resource api/v1/admin/orders"
                );
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleNoResourceFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody().get("error"));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new RuntimeException("Server error");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody().get("error"));
    }
}
