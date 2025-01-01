package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void testCreateValidEmail() {
        Email email = Email.create("test@example.com");
        assertNotNull(email);
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void testCreateEmailWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Email.create(null);
        });
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateEmailWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Email.create("");
        });
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateEmailWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Email.create("invalid-email");
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    void testEmailEqualityAndHashCode() {
        Email email1 = Email.create("test@example.com");
        Email email2 = Email.create("test@example.com");
        Email email3 = Email.create("different@example.com");

        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotEquals(email1.hashCode(), email3.hashCode());
    }

    @Test
    void testEmailToString() {
        Email email = Email.create("test@example.com");
        assertEquals("test@example.com", email.toString());
    }
}
