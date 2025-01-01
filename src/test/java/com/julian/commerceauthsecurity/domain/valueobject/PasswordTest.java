package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void testCreateValidPassword() {
        Password password = Password.create("SecurePassword123!");
        assertNotNull(password);
        assertEquals("SecurePassword123!", password.getValue());
    }

    @Test
    void testCreatePasswordWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Password.create(null);
        });
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreatePasswordWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Password.create("");
        });
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    void testPasswordFormatValidation() {
        assertTrue(Password.isValidFormat("Valid1!Password"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Password.isValidFormat("invalidpassword");
        });
        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character", exception.getMessage());
    }

    @Test
    void testPasswordEqualityAndHashCode() {
        Password password1 = Password.create("SecurePassword123!");
        Password password2 = Password.create("SecurePassword123!");
        Password password3 = Password.create("DifferentPassword1!");

        assertEquals(password1, password2);
        assertNotEquals(password1, password3);
        assertEquals(password1.hashCode(), password2.hashCode());
        assertNotEquals(password1.hashCode(), password3.hashCode());
    }

    @Test
    void testPasswordToString() {
        Password password = Password.create("SecurePassword123!");
        assertEquals("SecurePassword123!", password.toString());
    }
}
