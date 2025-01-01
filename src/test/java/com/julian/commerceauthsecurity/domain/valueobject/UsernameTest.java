package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    void testCreateValidUsername() {
        Username username = Username.create("validUser123");
        assertNotNull(username);
        assertEquals("validUser123", username.getValue());
    }

    @Test
    void testCreateUsernameWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Username.create(null);
        });
        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateUsernameWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Username.create("");
        });
        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateUsernameWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Username.create("@@invalid");
        });
        assertEquals("Username must contain only letters, numbers, dots, underscores, and hyphens", exception.getMessage());
    }

    @Test
    void testUsernameEqualityAndHashCode() {
        Username username1 = Username.create("user123");
        Username username2 = Username.create("user123");
        Username username3 = Username.create("anotherUser");

        assertEquals(username1, username2);
        assertNotEquals(username1, username3);
        assertEquals(username1.hashCode(), username2.hashCode());
        assertNotEquals(username1.hashCode(), username3.hashCode());
    }

    @Test
    void testUsernameToString() {
        Username username = Username.create("testUser");
        assertEquals("testUser", username.toString());
    }
}
