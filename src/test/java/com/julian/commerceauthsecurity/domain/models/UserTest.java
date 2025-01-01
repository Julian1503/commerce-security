package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private UUID userId;
    private Avatar avatar;
    private Username username;
    private Password password;
    private Email email;
    private Collection<Role> roles;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        avatar = Avatar.create("default.png");
        username = Username.create("testuser");
        password = Password.create("encryptedPassword");
        email = Email.create("test@example.com");
        roles = List.of(Role.getBasicRole());
        customerId = UUID.randomUUID();
    }

    @Test
    void testCreateUserWithValidData() {
        User user = User.create(userId, avatar, username, password, email, roles, customerId);
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals(avatar, user.getAvatar());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(roles, user.getRoles());
        assertTrue(user.isActive());
        assertEquals(customerId, user.getCustomerId());
    }

    @Test
    void testCreateUserWithEmptyRolesThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                User.create(userId, avatar, username, password, email, List.of(), customerId));
        assertEquals("Roles cannot be null or empty", exception.getMessage());
    }

    @Test
    void testChangePassword() {
        User user = User.create(userId, avatar, username, password, email, roles, customerId);
        user.changePassword("newEncryptedPassword");
        assertEquals("newEncryptedPassword", user.getPassword().getValue());
    }

    @Test
    void testUpdateUser() {
        User user = User.create(userId, avatar, username, password, email, roles, customerId);
        Avatar newAvatar = Avatar.create("new_avatar.png");
        Username newUsername = Username.create("newuser");
        Email newEmail = Email.create("new@example.com");

        User updatedUser = user.update(newAvatar, newUsername, newEmail);

        assertEquals(newAvatar, updatedUser.getAvatar());
        assertEquals(newUsername, updatedUser.getUsername());
        assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    void testAssignRoles() {
        User user = User.create(userId, avatar, username, password, email, roles, customerId);
        Role newRole = Role.create(UUID.randomUUID(), com.julian.commerceauthsecurity.domain.valueobject.SecurityName.create("ADMIN"), List.of());
        User updatedUser = user.assignRoles(List.of(newRole));

        assertTrue(updatedUser.getRoles().contains(newRole));
        assertEquals(2, updatedUser.getRoles().size());
    }

    @Test
    void testEqualityAndHashCode() {
        User user1 = User.create(userId, avatar, username, password, email, roles, customerId);
        User user2 = User.create(userId, avatar, username, password, email, roles, customerId);
        assertEquals(user1, user2);
        assertFalse(user1.equals(new Object()));
        assertFalse(user1.equals(null));
        assertTrue(user1.equals(user1));
        assertEquals(user1.hashCode(), user2.hashCode());

        User user3 = User.create(UUID.randomUUID(), avatar, username, password, email, roles, customerId);
        assertNotEquals(user1, user3);
    }
}
