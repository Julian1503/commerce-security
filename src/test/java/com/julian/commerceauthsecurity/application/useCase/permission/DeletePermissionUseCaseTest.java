package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.DeletePermissionCommand;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.PermissionBuilder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DeletePermissionUseCaseTest {
    @InjectMocks
    private DeletePermissionUseCase deletePermissionUseCase;
    @Mock
    private PermissionRepository permissionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteMethod_PermissionEmpty() {
        when(permissionRepository.findById(any())).thenReturn(Optional.empty());
        DeletePermissionCommand command = new DeletePermissionCommand(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> deletePermissionUseCase.execute(command));
    }

    @Test
    public void testExecuteMethod_PermissionNotEmpty() {
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        when(permissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));
        DeletePermissionCommand command = new DeletePermissionCommand(permission.getId());
        var result = deletePermissionUseCase.execute(command);
        assertEquals(result, permission);
    }
}
