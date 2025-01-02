package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import util.UserBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static util.UserBuilder.createUserWithParams;

class UserValidationTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
    }

    @Test
    void testValidateThrowsExceptionWhenUsernameExists() {
        // Arrange
        User user = UserBuilder.getValidUser();
        when(userRepository.existsByUsername(user.getUsername().getValue())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidation.validate(userRepository, user)
        );

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void testValidateThrowsExceptionWhenEmailExists() {
        // Arrange
        User user = UserBuilder.getValidUser();
        when(userRepository.existsByEmail(user.getEmail().getValue())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidation.validate(userRepository, user)
        );

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void testValidateDoesNotThrowWhenUserDoesNotExist() {
        // Arrange
        User user = UserBuilder.getValidUser();
        when(userRepository.existsByUsername(user.getUsername().getValue())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail().getValue())).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> UserValidation.validate(userRepository, user));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRoles")
    void testValidateThrowsExceptionWhenRolesAreNullOrEmpty(List<Role> roles) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> User.create(
                        UUID.randomUUID(),
                        Avatar.create("avatar-url"),
                        Username.create("testuser"),
                        Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4"),
                        Email.create("test@example.com"),
                        roles
                )
        );

        assertEquals("Roles cannot be null or empty", exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    void testValidateThrowsExceptionWhenUserIdIsNull(UUID userId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createUserWithParams(userId, Username.create("testuser"), Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4"), Email.create("test@example.com"))
        );

        assertEquals("User ID cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    void testValidateThrowsExceptionWhenUsernameIsNull(Username username) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createUserWithParams(UUID.randomUUID(), username, Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4"), Email.create("test@example.com"))
        );

        assertEquals("Username cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    void testValidateThrowsExceptionWhenPasswordIsNull(Password password) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createUserWithParams(UUID.randomUUID(), Username.create("testuser"), password, Email.create("test@example.com"))
        );

        assertEquals("Password cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    void testValidateThrowsExceptionWhenEmailIsNull(Email email) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createUserWithParams(UUID.randomUUID(), Username.create("testuser"), Password.create("4d65b6d7e1435e69cac07a8b5650384f566cb513ff5468379ede0dc560970ba9c7f9cb8e9ec877d2aa603115276b56d4"), email)
        );

        assertEquals("Email cannot be null", exception.getMessage());
    }

    private static Stream<List<Role>> provideInvalidRoles() {
        return Stream.of(
                null,
                Collections.emptyList()
        );
    }

}
