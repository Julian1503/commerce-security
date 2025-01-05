package com.julian.commerceauthsecurity.api.mapper;

import com.julian.commerceauthsecurity.api.response.UserResponse;
import com.julian.commerceauthsecurity.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.UserBuilder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseMapperTest {

    private UserResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserResponseMapper();
    }

    @Test
    void toSource_ShouldMapUserToUserResponse() {
        // Arrange
        User user = UserBuilder.getValidUser();

        // Act
        UserResponse response = mapper.toSource(user);

        // Assert
        assertNotNull(response);
        assertEquals(user.getUserId(), response.getId());
        assertEquals(user.getUsername().getValue(), response.getUsername());
        assertEquals(user.getEmail().getValue(), response.getEmail());
    }

    @Test
    void toSource_NullUser_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> mapper.toSource(null));
    }

    @Test
    void toTarget_ShouldThrowUnsupportedOperationException() {
        // Arrange
        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .username("testUser")
                .email("test@example.com")
                .build();

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> mapper.toTarget(response));
    }
}
