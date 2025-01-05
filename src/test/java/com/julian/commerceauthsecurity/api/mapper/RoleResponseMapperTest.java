package com.julian.commerceauthsecurity.api.mapper;

import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.api.response.RoleResponse;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PermissionBuilder;
import util.RoleBuilder;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleResponseMapperTest {

    private RoleResponseMapper mapper;
    private PermissionResponseMapper permissionMapper;

    @BeforeEach
    void setUp() {
        permissionMapper = mock(PermissionResponseMapper.class);
        mapper = new RoleResponseMapper(permissionMapper);
    }

    @Test
    void toSource_ShouldMapRoleToRoleResponse() {
        // Arrange
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        Role role = RoleBuilder.getBasicRole();

        PermissionResponse permissionResponse = PermissionResponse.builder()
                .id(permission.getId())
                .name(role.getPermissions().stream().toList().get(0).getName().getValue())
                .build();

        when(permissionMapper.toSource(permission)).thenReturn(permissionResponse);

        // Act
        RoleResponse response = mapper.toSource(role);

        // Assert
        assertNotNull(response);
        assertEquals(role.getId(), response.getId());
        assertEquals(role.getName().getValue(), response.getName());
        assertEquals(1, response.getPermissions().size());
    }

    @Test
    void toTarget_ShouldMapRoleResponseToRole() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        UUID permissionId = UUID.randomUUID();

        PermissionResponse permissionResponse = PermissionResponse.builder()
                .id(permissionId)
                .name("WRITE_PRIVILEGES")
                .build();

        RoleResponse roleResponse = RoleResponse.builder()
                .id(roleId)
                .name("USER")
                .permissions(Collections.singletonList(permissionResponse))
                .build();

        Permission permission = Permission.create(permissionId, SecurityName.create("WRITE_PRIVILEGES"));

        when(permissionMapper.toTarget(permissionResponse)).thenReturn(permission);

        // Act
        Role role = mapper.toTarget(roleResponse);

        // Assert
        assertNotNull(role);
        assertEquals(roleId, role.getId());
        assertEquals("USER", role.getName().getValue());
        assertEquals(1, role.getPermissions().size());
        assertEquals("WRITE_PRIVILEGES", role.getPermissions().stream().toList().get(0).getName().getValue());
    }
}
