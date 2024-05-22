package com.eventec.eventec.config;
import java.security.SecureRandom;
import java.util.Base64;

public class ValidationCodeGenerator {
    private static final SecureRandom random = new SecureRandom();
    private ValidationCodeGenerator() {
    }
    public static String generateValidationCode(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
