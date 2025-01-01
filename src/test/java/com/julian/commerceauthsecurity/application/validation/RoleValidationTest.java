package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.Test;
import util.RoleBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoleValidationTest {

    @Test
    void testValidateThrowsExceptionWhenRoleExists() {
        // Arrange
        RoleRepository roleRepository = mock(RoleRepository.class);
        Role role = RoleBuilder.getBasicRole();

        when(roleRepository.existsByName(role.getName().getValue())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> RoleValidation.validate(roleRepository, role)
        );

        assertEquals("Role already exists", exception.getMessage());
    }

    @Test
    void testValidateDoesNotThrowWhenRoleDoesNotExist() {
        // Arrange
        RoleRepository roleRepository = mock(RoleRepository.class);
        Role role = RoleBuilder.getBasicRole();

        when(roleRepository.existsByName(role.getName().getValue())).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> RoleValidation.validate(roleRepository, role));
    }
}
