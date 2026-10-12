package com.mousejerry.backend.util;

import java.security.SecureRandom;

public final class GameKeyGenerator {

    private static final String ALPHANUM = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private GameKeyGenerator() {
    }

    /**
     * Generates a 3-part game key, e.g., "4GF2A-9KL1P-QZ7XM".
     */
    public static String generateKey() {
        return generateSegment(5) + "-" + generateSegment(5) + "-" + generateSegment(5);
    }

    /**
     * Generates a unique order number prefix "ORD-", e.g., "ORD-9K8L2P4M".
     */
    public static String generateOrderNumber() {
        return "ORD-" + generateSegment(8);
    }

    private static String generateSegment(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(index));
        }
        return sb.toString();
    }
}
