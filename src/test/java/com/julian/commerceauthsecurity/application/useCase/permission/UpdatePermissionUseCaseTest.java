package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.UpdatePermissionCommand;
import com.julian.commerceauthsecurity.application.validation.PermissionValidation;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import util.PermissionBuilder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdatePermissionUseCaseTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private UpdatePermissionUseCase updatePermissionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldUpdatePermission_WhenPermissionExists() {
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        UpdatePermissionCommand command = new UpdatePermissionCommand(permission.getId(), permission.getName().getValue());

        when(permissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));

        try (MockedStatic<PermissionValidation> mockedValidation = mockStatic(PermissionValidation.class)) {
            mockedValidation.when(() -> PermissionValidation.validate(any(), any())).thenAnswer(invocation -> null);

            Permission result = updatePermissionUseCase.execute(command);

            assertNotNull(result);
            assertEquals(permission, result);
            verify(permissionRepository, times(1)).save(permission);

            mockedValidation.verify(() -> PermissionValidation.validate(any(), any()), times(1));
        }
    }

    @Test
    void execute_ShouldThrowException_WhenPermissionDoesNotExist() {
        UUID permissionId = UUID.randomUUID();
        UpdatePermissionCommand command = new UpdatePermissionCommand(permissionId, "NEW_NAME");

        when(permissionRepository.findById(permissionId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> updatePermissionUseCase.execute(command));
    }
}