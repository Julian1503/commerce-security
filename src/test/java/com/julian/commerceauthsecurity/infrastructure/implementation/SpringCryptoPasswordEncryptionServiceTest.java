package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.julian.commerceshared.security.SpringCrypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringCryptoPasswordEncryptionServiceTest {

    private SpringCryptoPasswordEncryptionService encryptionService;

    @BeforeEach
    void setUp() {
        encryptionService = new SpringCryptoPasswordEncryptionService();
    }

    @Test
    void testEncrypt_ValidInput_ReturnsEncryptedText() {
        // Arrange
        String rawText = "password123";

        // Act
        String encryptedText = encryptionService.encrypt(rawText);

        // Assert
        assertNotNull(encryptedText);
        assertNotEquals(rawText, encryptedText);
    }

    @Test
    void testDecrypt_ValidInput_ReturnsOriginalText() {
        // Arrange
        String rawText = "password123";
        String encryptedText = SpringCrypto.encrypt(rawText);

        // Act
        String decryptedText = encryptionService.decrypt(encryptedText);

        // Assert
        assertEquals(rawText, decryptedText);
    }

    @Test
    void testMatches_ValidMatch_ReturnsTrue() {
        // Arrange
        String rawText = "password123";
        String encryptedText = SpringCrypto.encrypt(rawText);

        // Act
        boolean isMatch = encryptionService.matches(rawText, encryptedText);

        // Assert
        assertTrue(isMatch);
    }

    @Test
    void testMatches_InvalidMatch_ReturnsFalse() {
        // Arrange
        String rawText = "password123";
        String encryptedText = SpringCrypto.encrypt("differentPassword");

        // Act
        boolean isMatch = encryptionService.matches(rawText, encryptedText);

        // Assert
        assertFalse(isMatch);
    }
}
