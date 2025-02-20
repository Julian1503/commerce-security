package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import util.PermissionBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PermissionValidationTest {

    @Test
    void testValidatePermissionWhenPermissionExists() {
        // Arrange
        PermissionRepository permissionRepository = mock(PermissionRepository.class);
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        when(permissionRepository.existsByName(permission.getName().getValue())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PermissionValidation.validate(permissionRepository, permission));
        assertEquals("Permission already exists", exception.getMessage());
    }

    @Test
    void testValidatePermissionWhenPermissionDoesNotExist() {
        // Arrange
        PermissionRepository permissionRepository = mock(PermissionRepository.class);
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        when(permissionRepository.existsByName(permission.getName().getValue())).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> PermissionValidation.validate(permissionRepository, permission));
    }
}
