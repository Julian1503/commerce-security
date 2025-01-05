package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PermissionBuilder;
import util.RoleBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleMapperTest {

    private RoleMapper mapper;
    private PermissionMapper permissionMapper;

    @BeforeEach
    void setUp() {
        permissionMapper = mock(PermissionMapper.class);
        mapper = new RoleMapper(permissionMapper);
    }

    @Test
    void testToSource_ValidRole_ReturnsRoleEntity() {
        Role role = RoleBuilder.getBasicRole();
        PermissionEntity permissionEntity = PermissionBuilder.createPermissionEntityWithRandomName();
        when(permissionMapper.toSource(any())).thenReturn(permissionEntity);
        RoleEntity entity = mapper.toSource(role);

        assertNotNull(entity);
        assertEquals(role.getId(), entity.getId());
        assertEquals(role.getName().getValue(), entity.getName().toUpperCase());
    }

    @Test
    void testToSource_NullRole_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.toSource(null));
        assertEquals("RoleMapper.toSource: Role Model cannot be null", thrown.getMessage());
    }

    @Test
    void testToTarget_ValidRoleEntity_ReturnsRole() {
        RoleEntity entity = RoleBuilder.getBasicRoleEntity();
        Permission permission = PermissionBuilder.createPermissionWithRandomName();
        when(permissionMapper.toTarget(any())).thenReturn(permission);

        Role role = mapper.toTarget(entity);

        assertNotNull(role);
        assertEquals(entity.getId(), role.getId());
        assertEquals(entity.getName(), role.getName().getValue());
        assertEquals(1, role.getPermissions().size());
    }

    @Test
    void testToTarget_NullRoleEntity_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.toTarget(null));
        assertEquals("RoleMapper.toTarget: Role Entity cannot be null", thrown.getMessage());
    }
}
