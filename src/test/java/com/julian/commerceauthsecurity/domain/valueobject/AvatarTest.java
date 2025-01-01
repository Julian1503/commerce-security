package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvatarTest {

    @Test
    void testCreateValidAvatar() {
        Avatar avatar = Avatar.create("http://example.com/avatar.png");
        assertNotNull(avatar);
        assertEquals("http://example.com/avatar.png", avatar.getValue());
    }

    @Test
    void testCreateAvatarWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Avatar.create(null);
        });
        assertEquals("Avatar cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateAvatarWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Avatar.create("");
        });
        assertEquals("Avatar cannot be null or empty", exception.getMessage());
    }

    @Test
    void testGetDefaultAvatar() {
        Avatar defaultAvatar = Avatar.getDefaultAvatar();
        assertNotNull(defaultAvatar);
        assertEquals("https://www.gravatar.com/avatar/", defaultAvatar.getValue());
    }

    @Test
    void testAvatarEqualityAndHashCode() {
        Avatar avatar1 = Avatar.create("http://example.com/avatar.png");
        Avatar avatar2 = Avatar.create("http://example.com/avatar.png");
        Avatar avatar3 = Avatar.create("http://example.com/different.png");

        assertEquals(avatar1, avatar2);
        assertNotEquals(avatar1, avatar3);
        assertEquals(avatar1.hashCode(), avatar2.hashCode());
        assertNotEquals(avatar1.hashCode(), avatar3.hashCode());
    }

    @Test
    void testAvatarToString() {
        Avatar avatar = Avatar.create("http://example.com/avatar.png");
        assertEquals("http://example.com/avatar.png", avatar.toString());
    }
}
