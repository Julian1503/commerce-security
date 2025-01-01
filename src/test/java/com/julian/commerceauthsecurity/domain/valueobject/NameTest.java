package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void testCreateValidName() {
        Name name = Name.create("JohnDoe");
        assertNotNull(name);
        assertEquals("JohnDoe", name.getValue());
    }

    @Test
    void testCreateNameWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Name.create(null);
        });
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNameWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Name.create("");
        });
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNameWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Name.create("@InvalidName");
        });
        assertEquals("Name must contain only letters, numbers, dots, underscores, and hyphens", exception.getMessage());
    }

    @Test
    void testNameEqualityAndHashCode() {
        Name name1 = Name.create("JohnDoe");
        Name name2 = Name.create("JohnDoe");
        Name name3 = Name.create("JaneDoe");

        assertEquals(name1, name2);
        assertNotEquals(name1, name3);
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name1.hashCode(), name3.hashCode());
    }

    @Test
    void testNameToString() {
        Name name = Name.create("JohnDoe");
        assertEquals("JohnDoe", name.toString());
    }
}
