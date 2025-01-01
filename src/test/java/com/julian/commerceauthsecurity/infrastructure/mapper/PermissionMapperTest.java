package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PermissionBuilder;


import static java.util.UUID.*;
import static org.junit.jupiter.api.Assertions.*;

class PermissionMapperTest {

    private PermissionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PermissionMapper();
    }

    @Test
    void testToSource_ValidPermission_ReturnsPermissionEntity() {
        Permission permission = PermissionBuilder.createPermissionWithRandomName();

        PermissionEntity entity = mapper.toSource(permission);

        assertNotNull(entity);
        assertEquals(permission.getId(), entity.getId());
        assertEquals(permission.getName().getValue(), entity.getName());
    }

    @Test
    void testToSource_NullPermission_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.toSource(null));
        assertEquals("PermissionMapper.toSource: Permission Model cannot be null", thrown.getMessage());
    }

    @Test
    void testToTarget_ValidPermissionEntity_ReturnsPermission() {
        PermissionEntity entity = PermissionBuilder.createPermissionEntityWithRandomName();

        Permission permission = mapper.toTarget(entity);

        assertNotNull(permission);
        assertEquals(entity.getId(), permission.getId());
        assertEquals(entity.getName().toUpperCase(), permission.getName().getValue());
    }

    @Test
    void testToTarget_NullPermissionEntity_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.toTarget(null));
        assertEquals("PermissionMapper.toTarget: Permission Entity cannot be null", thrown.getMessage());
    }
}
