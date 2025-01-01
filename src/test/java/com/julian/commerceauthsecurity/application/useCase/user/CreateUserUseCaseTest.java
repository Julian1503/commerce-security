package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.CreateBasicUserCommand;
import com.julian.commerceauthsecurity.application.validation.UserValidation;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.EncryptionService;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserUseCaseTest {
    @InjectMocks
    private CreateUserUseCase createUserUseCase;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EncryptionService encryptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteMethod() {
        CreateBasicUserCommand command = new CreateBasicUserCommand("mockName", "mockEmail@ho.com", "mockPassword");
        UUID id = UUID.randomUUID();

        when(encryptionService.encrypt(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(id);

        try (MockedStatic<UserValidation> mockedValidation = mockStatic(UserValidation.class)) {
            mockedValidation.when(() -> UserValidation.validate(any(UserRepository.class), any(User.class)))
                    .thenAnswer(invocation -> null);

            UUID result = createUserUseCase.execute(command);

            // Assert
            assertEquals(id, result);

            verify(encryptionService).encrypt(command.password());
            verify(userRepository).save(any(User.class));

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(captor.capture());
            User capturedUser = captor.getValue();

            assertEquals("mockName", capturedUser.getUsername().getValue());
            assertEquals("mockEmail@ho.com", capturedUser.getEmail().getValue());
            assertEquals("hashedPassword", capturedUser.getPassword().getValue());
            assertEquals(Avatar.getDefaultAvatar(), capturedUser.getAvatar());
            assertEquals(Role.getDefaultRoles(), capturedUser.getRoles());

            verifyNoMoreInteractions(encryptionService, userRepository);

        }
    }
}
