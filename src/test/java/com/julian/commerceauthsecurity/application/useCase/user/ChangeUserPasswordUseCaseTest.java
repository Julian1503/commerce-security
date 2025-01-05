package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.ChangePasswordCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.EncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.UserBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChangeUserPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private ChangeUserPasswordUseCase changeUserPasswordUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldChangePassword_WhenOldPasswordMatchesAndNewPasswordValid() {
        User user = UserBuilder.getValidUser();
        ChangePasswordCommand command = new ChangePasswordCommand(user.getUsername().getValue(), "oldPassword123!", "P@ssw0rd1!");
        when(userRepository.findByUsername(command.username())).thenReturn(Optional.of(user));
        when(encryptionService.matches(command.oldPassword(), user.getPassword().getValue())).thenReturn(true);
        when(encryptionService.encrypt(command.newPassword())).thenReturn("6548a6cd0fe8da4d0ce4aba1d1b2b3a3b3b7c30ee0f22487f009f5ebaf23e88f041a3ca6e68208ec2d0d69bff4818bc1");

        boolean result = changeUserPasswordUseCase.execute(command);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void execute_ShouldThrowException_WhenOldPasswordDoesNotMatch() {
        User user = UserBuilder.getValidUser();
        ChangePasswordCommand command = new ChangePasswordCommand(user.getUsername().getValue(), "oldPassword", "newPassword");

        when(userRepository.findByUsername(command.username())).thenReturn(Optional.of(user));
        when(encryptionService.matches(command.oldPassword(), user.getPassword().getValue())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> changeUserPasswordUseCase.execute(command));
    }

    @Test
    void execute_ShouldThrowException_WhenNewPasswordDoesNotMatch() {
        User user = UserBuilder.getValidUser();
        ChangePasswordCommand command = new ChangePasswordCommand(user.getUsername().getValue(), "oldPassword", "newPassword");

        when(userRepository.findByUsername(command.username())).thenReturn(Optional.of(user));
        when(encryptionService.matches(command.oldPassword(), user.getPassword().getValue())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> changeUserPasswordUseCase.execute(command));
    }

    @Test
    void execute_ShouldReturnFalse_WhenUserNotFound() {
        ChangePasswordCommand command = new ChangePasswordCommand("username", "oldPassword", "newPassword");

        when(userRepository.findByUsername(command.username())).thenReturn(Optional.empty());

        boolean result = changeUserPasswordUseCase.execute(command);

        assertFalse(result);
    }
}
