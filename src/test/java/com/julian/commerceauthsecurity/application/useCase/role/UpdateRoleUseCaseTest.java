package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.application.validation.RoleValidation;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import util.RoleBuilder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateRoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UpdateRoleUseCase updateRoleUseCase;

    @Mock
    private RoleValidation roleValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldUpdateRole_WhenRoleExists() {
        Role role = RoleBuilder.getBasicRole();
        UpdateRoleCommand command = new UpdateRoleCommand(role.getId(), "NEW_ROLE_NAME");

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        try (MockedStatic<RoleValidation> mockedValidation = mockStatic(RoleValidation.class)) {
            mockedValidation.when(() -> RoleValidation.validate(any(), any())).thenAnswer(invocation -> null);

            Role result = updateRoleUseCase.execute(command);

            assertNotNull(result);
            assertEquals(role, result);
            verify(roleRepository, times(1)).save(role);

            mockedValidation.verify(() -> RoleValidation.validate(any(), any()), times(1));
        }
    }

    @Test
    void execute_ShouldThrowException_WhenRoleDoesNotExist() {
        UUID roleId = UUID.randomUUID();
        UpdateRoleCommand command = new UpdateRoleCommand(roleId, "NEW_ROLE_NAME");

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> updateRoleUseCase.execute(command));
    }
}
