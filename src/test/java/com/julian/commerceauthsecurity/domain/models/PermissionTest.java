package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    private UUID permissionId;
    private SecurityName permissionName;

    @BeforeEach
    void setUp() {
        permissionId = UUID.randomUUID();
        permissionName = SecurityName.create("READ");
    }

    @Test
    void testCreatePermissionWithValidData() {
        Permission permission = Permission.create(permissionId, permissionName);
        assertNotNull(permission);
        assertEquals(permissionId, permission.getId());
        assertEquals(permissionName, permission.getName());
    }

    @Test
    void testGetBasicPermission() {
        Permission basicPermission = Permission.getBasicPermission();
        assertNotNull(basicPermission);
        assertEquals("READ_USER", basicPermission.getName().getValue());
    }

    @Test
    void testEqualityAndHashCode() {
        Permission permission1 = Permission.create(permissionId, permissionName);
        Permission permission2 = Permission.create(permissionId, permissionName);

        assertEquals(permission1, permission2);
        assertEquals(permission1.hashCode(), permission2.hashCode());

        Permission permission3 = Permission.create(UUID.randomUUID(), permissionName);
        assertNotEquals(permission1, permission3);
    }

    @Test
    void testUpdatePermission() {
        Permission permission = Permission.create(permissionId, permissionName);
        SecurityName newName = SecurityName.create("WRITE");

        Permission updatedPermission = permission.update(newName);

        assertEquals(newName, updatedPermission.getName());
        assertEquals(permissionId, updatedPermission.getId());
    }

    @Test
    void testGetAuthority() {
        Permission permission = Permission.create(permissionId, permissionName);
        assertEquals(permissionName.getValue(), permission.getAuthority());
    }
}
