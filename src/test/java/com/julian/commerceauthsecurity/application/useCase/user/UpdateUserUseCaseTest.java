package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.UpdateUserCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.UserBuilder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldUpdateUser_WhenUserExists() {
        UUID userId = UUID.randomUUID();
        UpdateUserCommand command = new UpdateUserCommand(userId, "newUsername", "newEmail@test.com", "avatar.png");
        User user = UserBuilder.getValidUser();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User result = updateUserUseCase.execute(command);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void execute_ShouldThrowException_WhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UpdateUserCommand command = new UpdateUserCommand(userId, "newUsername", "newEmail@test.com", "avatar.png");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(command));
    }
}
