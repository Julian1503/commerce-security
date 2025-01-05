package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.DeleteUserCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DeleteUserUseCaseTest {
    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteMethod_UserEmpty() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        DeleteUserCommand command = new DeleteUserCommand(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> deleteUserUseCase.execute(command));
    }

    @Test
    public void testExecuteMethod_UserNotEmpty() {
        UUID id = UUID.randomUUID();
        User user = UserBuilder.getValidUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        DeleteUserCommand command = new DeleteUserCommand(id);
        var result = deleteUserUseCase.execute(command);
        assertEquals(result, user);
    }
}
