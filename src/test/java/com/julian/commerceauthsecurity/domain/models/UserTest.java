package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private UUID userId;
    private Avatar avatar;
    private Username username;
    private Password password;
    private Email email;
    private Collection<Role> roles;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        avatar = Avatar.create("default.png");
        username = Username.create("testuser");
        password = Password.create("6548a6cd0fe8da4d0ce4aba1d1b2b3a3b3b7c30ee0f22487f009f5ebaf23e88f041a3ca6e68208ec2d0d69bff4818bc1");
        email = Email.create("test@example.com");
        roles = List.of(Role.getBasicRole());
    }

    @Test
    void testCreateUserWithValidData() {
        User user = User.create(userId, avatar, username, password, email, roles);
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals(avatar, user.getAvatar());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(roles, user.getRoles());
        assertTrue(user.isActive());
    }

    @Test
    void testCreateUserWithEmptyRolesThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                User.create(userId, avatar, username, password, email, List.of()));
        assertEquals("Roles cannot be null or empty", exception.getMessage());
    }

    @Test
    void testChangePassword() {
        User user = User.create(userId, avatar, username, password, email, roles);
        user.changePassword("6548a6cd0fe8da4d0ce4aba1d1b2b3a3b3b7c30ee0f22487f009f5ebaf23e88f041a3ca6e68208ec2d0d69bff4818bc1");
        assertEquals("6548a6cd0fe8da4d0ce4aba1d1b2b3a3b3b7c30ee0f22487f009f5ebaf23e88f041a3ca6e68208ec2d0d69bff4818bc1", user.getPassword().getValue());
    }

    @Test
    void testUpdateUser() {
        User user = User.create(userId, avatar, username, password, email, roles);
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
        User user = User.create(userId, avatar, username, password, email, roles);
        Role newRole = Role.create(UUID.randomUUID(), com.julian.commerceauthsecurity.domain.valueobject.SecurityName.create("ADMIN"), List.of());
        User updatedUser = user.assignRoles(List.of(newRole));

        assertTrue(updatedUser.getRoles().contains(newRole));
        assertEquals(2, updatedUser.getRoles().size());
    }

    @Test
    void testEqualityAndHashCode() {
        User user1 = User.create(userId, avatar, username, password, email, roles);
        User user2 = User.create(userId, avatar, username, password, email, roles);
        assertEquals(user1, user2);
        assertNotEquals(user1, new Object());
        assertNotEquals(null, user1);
        assertEquals(user1, user1);
        assertEquals(user1.hashCode(), user2.hashCode());

        User user3 = User.create(UUID.randomUUID(), avatar, username, password, email, roles);
        assertNotEquals(user1, user3);
    }
}
