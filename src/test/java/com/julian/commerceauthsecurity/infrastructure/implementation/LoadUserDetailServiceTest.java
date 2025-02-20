package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.julian.commerceauthsecurity.domain.models.JwtUser;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import util.UserBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoadUserDetailServiceTest {

    @InjectMocks
    private LoadUserDetailService loadUserDetailService;

    @Mock
    private UserJpaRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_ValidUsername_ReturnsUserDetails() {
        // Arrange
        UserEntity userEntity = UserBuilder.getValidUserEntity();

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = loadUserDetailService.loadUserByUsername(userEntity.getUsername());

        // Assert
        assertNotNull(userDetails);
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    void testLoadUserByUsername_InvalidUsername_ThrowsException() {
        // Arrange
        String username = "unknownuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> loadUserDetailService.loadUserByUsername(username));
    }

    @Test
    void testGetUserDetails_ValidToken_ReturnsUserDetails() {
        // Arrange
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqdWxpYW4xMjMiLCJzY29wZSI6WyJVU0VSIl0sImlzcyI6InNlbGYiLCJleHAiOjE3MzU0NjIyNTIsImlhdCI6MTczNTM3NTg1Miwic2NpZCI6ImRjOTczYzRjLTgwYWYtNDQ5Zi05MGVhLWU4ZDg3NGUxNzUxNSIsImVtYWlsIjoianVsaWFuQGV4YW1wbGUuY29tIiwidXNlcm5hbWUiOiJqdWxpYW4xMjMifQ.K-fKDW53azK4kVFshE9b2astMypv8jFxkrfHNn96OJo4_AEVnmO7b9IU_LRQmGbGdGCnKA-yadX1GTPaLLYlLHEvuXr6YfXDbvfxkTAOZ8OSPRceHvBqjfaduhwYm9sGRV0MkUt2_XnbkkEG9ZvXQz8fcV_of_BTbv2j6r1z_hVv51Lm5meNjqalXvMRXlHdhQWp5umTCcedjpk2eSDO-jFvzGmVuGxY57FvbKRW0oREDis62vFq2QHQBz4cKgdZoi8dkwiHG_5NJnGIbWIOcxiJoJt6ohhuSc7xI2w3HS9L6ph88U0My-ZyOVB-MViRbL1Gh3erdCMOL1SBw-QGZQ";
        JwtUser mockJwtUser = mock(JwtUser.class);
        when(mockJwtUser.getUsername()).thenReturn("julian123");

        // Act
        UserDetails userDetails = loadUserDetailService.getUserDetails(token);

        // Assert
        assertNotNull(userDetails);
        assertEquals("julian123", userDetails.getUsername());
    }

    @Test
    void testGetUserDetails_ValidToken_JwtClaimScopeNull() {
        // Arrange
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqdWxpYW4xMjMiLCJlbWFpbF9oYXNoIjotNzk3ODAxMDcxLCJpc3MiOiJzZWxmIiwiZXhwIjoxNzM1ODc2MzM2LCJpYXQiOjE3MzU3ODk5MzYsImp0aSI6IjZiMWJjZGMzLTdlMDktNDljZC1hOGM5LWU2ODM5OTlkMDRlYSIsInNjaWQiOiJkYzk3M2M0Yy04MGFmLTQ0OWYtOTBlYS1lOGQ4NzRlMTc1MTUiLCJ1c2VybmFtZSI6Imp1bGlhbjEyMyJ9.jD41LrIaHBrJipO24IW6zhNxDOxmHwx3XOPBKs9u5SIBY9U7vf3KbbBm0vh3wTlJbDJOdd8nnfaBOvF-d-V7mEVT9MtjDeitHQQ5HOUi8lny_aT-eeITGvDonC8kiiYAKr3ni7ZovaZTHQbnijq8H1Ria8tAPneVtdd3qwzV3DSXUOrqA9I6qpauHsw7rhNodLc5ZTR89RWXF9AAoLEk5jIebou0bGzDNsOU40asmXiCxMFbYP0piz_xvs8rwcTQMmTQUuNp438OWGNl5iHiNP8ME6e8Gs-weICUDTuxeezPptvQo9aOeNUk0KCgZNHNnAbqvcg8Bx8GIUCT3WyWrg";
        JwtUser mockJwtUser = mock(JwtUser.class);
        when(mockJwtUser.getUsername()).thenReturn("julian123");

        // Act
        UserDetails userDetails = loadUserDetailService.getUserDetails(token);

        // Assert
        assertNotNull(userDetails);
        assertEquals("julian123", userDetails.getUsername());
    }

    @Test
    void testGetUserDetails_InvalidToken_ReturnsNull() {
        // Arrange
        String token = "invalid.jwt.token";

        // Act
        UserDetails userDetails = loadUserDetailService.getUserDetails(token);

        // Assert
        assertNull(userDetails);
    }
}
