package com.mousejerry.backend.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameKeyGeneratorTest {

    @Test
    void testGenerateKeyFormat() {
        String key = GameKeyGenerator.generateKey();
        assertNotNull(key);
        assertTrue(key.matches("^[2-9A-Z]{5}-[2-9A-Z]{5}-[2-9A-Z]{5}$"), "Key must match 5-5-5 format: " + key);
    }

    @Test
    void testGenerateKeyUniqueness() {
        String key1 = GameKeyGenerator.generateKey();
        String key2 = GameKeyGenerator.generateKey();
        assertNotEquals(key1, key2);
    }

    @Test
    void testGenerateOrderNumberFormat() {
        String orderNumber = GameKeyGenerator.generateOrderNumber();
        assertNotNull(orderNumber);
        assertTrue(orderNumber.startsWith("ORD-"));
        assertEquals(12, orderNumber.length()); // "ORD-" (4) + 8 chars = 12
    }

    @Test
    void testGenerateOrderNumberUniqueness() {
        String num1 = GameKeyGenerator.generateOrderNumber();
        String num2 = GameKeyGenerator.generateOrderNumber();
        assertNotEquals(num1, num2);
    }
}
