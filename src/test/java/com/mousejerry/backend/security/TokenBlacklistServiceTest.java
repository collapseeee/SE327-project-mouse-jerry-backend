package com.mousejerry.backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenBlacklistServiceTest {

    private TokenBlacklistService tokenBlacklistService;

    @BeforeEach
    void setUp() {
        tokenBlacklistService = new TokenBlacklistService();
    }

    @Test
    void testBlacklistToken() {
        String token = "sample-token-123";
        assertFalse(tokenBlacklistService.isBlacklisted(token));

        tokenBlacklistService.blacklistToken(token);
        assertTrue(tokenBlacklistService.isBlacklisted(token));
    }

    @Test
    void testBlacklistNullOrBlankToken() {
        tokenBlacklistService.blacklistToken(null);
        tokenBlacklistService.blacklistToken("   ");

        assertFalse(tokenBlacklistService.isBlacklisted(null));
        assertFalse(tokenBlacklistService.isBlacklisted("   "));
        assertFalse(tokenBlacklistService.isBlacklisted("unknown"));
    }
}
