package com.julian.commerceauthsecurity.api.mapper;

import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PermissionResponseMapperTest {

    private PermissionResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PermissionResponseMapper();
    }

    @Test
    void toSource_ShouldMapPermissionToPermissionResponse() {
        // Arrange
        UUID permissionId = UUID.randomUUID();
        Permission permission = Permission.create(permissionId, SecurityName.create("READ_PRIVILEGES"));

        // Act
        PermissionResponse response = mapper.toSource(permission);

        // Assert
        assertNotNull(response);
        assertEquals(permissionId, response.getId());
        assertEquals("READ_PRIVILEGES", response.getName());
    }

    @Test
    void toTarget_ShouldMapPermissionResponseToPermission() {
        // Arrange
        UUID permissionId = UUID.randomUUID();
        PermissionResponse response = PermissionResponse.builder()
                .id(permissionId)
                .name("WRITE_PRIVILEGES")
                .build();

        // Act
        Permission permission = mapper.toTarget(response);

        // Assert
        assertNotNull(permission);
        assertEquals(permissionId, permission.getId());
        assertEquals("WRITE_PRIVILEGES", permission.getName().getValue());
    }
}
