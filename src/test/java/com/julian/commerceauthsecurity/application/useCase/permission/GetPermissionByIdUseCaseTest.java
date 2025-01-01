package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.query.permission.GetPermissionByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.PermissionBuilder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetPermissionByIdUseCaseTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private GetPermissionByIdUseCase getPermissionByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldReturnPermission_WhenPermissionExists() {
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        when(permissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));

        Permission result = getPermissionByIdUseCase.execute(new GetPermissionByIdQuery(permission.getId()));

        assertNotNull(result);
        assertEquals(permission, result);
        verify(permissionRepository, times(1)).findById(permission.getId());
    }

    @Test
    void execute_ShouldThrowException_WhenPermissionDoesNotExist() {
        UUID permissionId = UUID.randomUUID();
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> getPermissionByIdUseCase.execute(new GetPermissionByIdQuery(permissionId)));
        verify(permissionRepository, times(1)).findById(permissionId);
    }
}
