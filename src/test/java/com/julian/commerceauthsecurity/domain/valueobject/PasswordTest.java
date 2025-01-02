package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void testCreateValidPassword() {
        Password password = Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4!");
        assertNotNull(password);
        assertEquals("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4!", password.getValue());
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
        boolean result = Password.isValidFormat("invalidpassword");
        assertFalse(result);
    }

    @Test
    void testPasswordEqualityAndHashCode() {
        Password password1 = Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4!");
        Password password2 = Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4!");
        Password password3 = Password.create("DifferentPassword1!");

        assertEquals(password1, password2);
        assertNotEquals(password1, password3);
        assertEquals(password1.hashCode(), password2.hashCode());
        assertNotEquals(password1.hashCode(), password3.hashCode());
    }

    @Test
    void testPasswordToString() {
        Password password = Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4!");
        assertEquals("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4!", password.toString());
    }
}
