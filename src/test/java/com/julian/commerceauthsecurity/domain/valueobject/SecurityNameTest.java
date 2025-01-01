package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityNameTest {

    @Test
    void testCreateValidSecurityName() {
        SecurityName securityName = SecurityName.create("Admin");
        assertNotNull(securityName);
        assertEquals("ADMIN", securityName.getValue());
    }

    @Test
    void testCreateSecurityNameWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SecurityName.create(null);
        });
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateSecurityNameWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SecurityName.create("");
        });
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateSecurityNameWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SecurityName.create("@Invalid");
        });
        assertEquals("Name must contain only letters, numbers, dots, underscores, and hyphens", exception.getMessage());
    }

    @Test
    void testSecurityNameEqualityAndHashCode() {
        SecurityName name1 = SecurityName.create("Admin");
        SecurityName name2 = SecurityName.create("Admin");
        SecurityName name3 = SecurityName.create("User");

        assertEquals(name1, name2);
        assertNotEquals(name1, name3);
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name1.hashCode(), name3.hashCode());
    }

    @Test
    void testSecurityNameToString() {
        SecurityName securityName = SecurityName.create("Admin");
        assertEquals("ADMIN", securityName.toString());
    }
}
