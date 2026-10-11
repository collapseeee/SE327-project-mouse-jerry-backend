package com.mousejerry.backend.security;

import com.mousejerry.backend.entity.Role;
import com.mousejerry.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600000L);
    }

    @Test
    void testGenerateAndValidateToken() {
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        String token = jwtUtils.generateToken(user);
        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token));

        assertEquals("testuser", jwtUtils.getUsernameFromToken(token));
        assertEquals(1L, jwtUtils.getUserIdFromToken(token));
        assertEquals("ROLE_CUSTOMER", jwtUtils.getRoleFromToken(token));
    }

    @Test
    void testValidateInvalidToken() {
        assertFalse(jwtUtils.validateToken("invalid.token.value"));
        assertFalse(jwtUtils.validateToken(null));
        assertFalse(jwtUtils.validateToken(""));
    }
}
