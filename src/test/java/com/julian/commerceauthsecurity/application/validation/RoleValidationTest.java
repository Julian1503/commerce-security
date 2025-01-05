package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import util.RoleBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
