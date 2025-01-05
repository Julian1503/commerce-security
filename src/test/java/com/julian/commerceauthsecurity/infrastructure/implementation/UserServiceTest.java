package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import util.UserBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserFromAuthentication_ValidAuthentication_ReturnsUser() {
        // Arrange
        User user = UserBuilder.getValidUser();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(user.getUsername().getValue());
        when(userRepository.findByUsername(user.getUsername().getValue())).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserFromAuthentication(authentication);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testGetUserFromAuthentication_UserNotFound_ReturnsEmpty() {
        // Arrange
        String username = "unknownuser";
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserFromAuthentication(authentication);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserFromAuthentication_InvalidAuthentication_ThrowsException() {
        // Arrange
        when(authentication.getPrincipal()).thenThrow(ClassCastException.class);

        // Act & Assert
        assertThrows(ClassCastException.class, () -> userService.getUserFromAuthentication(authentication));
    }
}
