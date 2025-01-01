package com.julian.commerceauthsecurity.infrastructure.security;

import com.julian.commerceshared.security.SpringCrypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderConfigTest {

    private PasswordEncoderConfig passwordEncoderConfig;

    @BeforeEach
    void setUp() {
        passwordEncoderConfig = new PasswordEncoderConfig();
    }

    @Test
    @DisplayName("Test encode method")
    void testEncode() {
        // Arrange
        String rawPassword = "password123";
        String encryptedPassword = "encryptedPassword123";

        // Mock SpringCrypto.encrypt
        try (MockedStatic<SpringCrypto> springCryptoMock = Mockito.mockStatic(SpringCrypto.class)) {
            springCryptoMock.when(() -> SpringCrypto.encrypt(rawPassword)).thenReturn(encryptedPassword);

            // Act
            String result = passwordEncoderConfig.encode(rawPassword);

            // Assert
            assertEquals(encryptedPassword, result, "The encoded password should match the mocked encrypted password.");
        }
    }

    @Test
    @DisplayName("Test matches method when passwords match")
    void testMatches_WhenPasswordsMatch() {
        // Arrange
        String rawPassword = "password123";
        String encryptedPassword = "encryptedPassword123";

        // Mock SpringCrypto.decrypt
        try (MockedStatic<SpringCrypto> springCryptoMock = Mockito.mockStatic(SpringCrypto.class)) {
            springCryptoMock.when(() -> SpringCrypto.decrypt(encryptedPassword)).thenReturn(rawPassword);

            // Act
            boolean result = passwordEncoderConfig.matches(rawPassword, encryptedPassword);

            // Assert
            assertTrue(result, "The passwords should match.");
        }
    }

    @Test
    @DisplayName("Test matches method when passwords do not match")
    void testMatches_WhenPasswordsDoNotMatch() {
        // Arrange
        String rawPassword = "password123";
        String encryptedPassword = "differentEncryptedPassword";

        // Mock SpringCrypto.decrypt
        try (MockedStatic<SpringCrypto> springCryptoMock = Mockito.mockStatic(SpringCrypto.class)) {
            springCryptoMock.when(() -> SpringCrypto.decrypt(encryptedPassword)).thenReturn("wrongPassword");

            // Act
            boolean result = passwordEncoderConfig.matches(rawPassword, encryptedPassword);

            // Assert
            assertFalse(result, "The passwords should not match.");
        }
    }

    @Test
    @DisplayName("Test upgradeEncoding method")
    void testUpgradeEncoding() {
        // Arrange
        String encodedPassword = "encodedPassword123";

        // Act
        boolean result = passwordEncoderConfig.upgradeEncoding(encodedPassword);

        // Assert
        assertFalse(result, "By default, upgradeEncoding should return false.");
    }
}
