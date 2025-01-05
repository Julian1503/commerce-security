package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private UUID roleId;
    private SecurityName roleName;
    private Collection<Permission> permissions;

    @BeforeEach
    void setUp() {
        roleId = UUID.randomUUID();
        roleName = SecurityName.create("USER");
        permissions = List.of(Permission.getBasicPermission());
    }

    @Test
    void testCreateRoleWithValidData() {
        Role role = Role.create(roleId, roleName, permissions);
        assertNotNull(role);
        assertEquals(roleId, role.getId());
        assertEquals(roleName, role.getName());
        assertEquals(permissions, role.getPermissions());
    }

    @Test
    void testGetBasicRole() {
        Role basicRole = Role.getBasicRole();
        assertNotNull(basicRole);
        assertEquals("USER", basicRole.getName().getValue());
    }

    @Test
    void testHasPermission() {
        Role role = Role.create(roleId, roleName, permissions);
        assertTrue(role.hasPermission("READ_USER"));
        assertFalse(role.hasPermission("ADMIN"));
    }

    @Test
    void testAddPermission() {
        Role role = Role.create(roleId, roleName, permissions);
        Permission newPermission = Permission.create(UUID.randomUUID(), SecurityName.create("ADMIN"));

        Role updatedRole = role.addPermission(newPermission);

        assertTrue(updatedRole.getPermissions().contains(newPermission));
        assertEquals(2, updatedRole.getPermissions().size());
    }

    @Test
    void testEqualityAndHashCode() {
        Role role1 = Role.create(roleId, roleName, permissions);
        Role role2 = Role.create(roleId, roleName, permissions);

        assertEquals(role1, role2);
        assertNotEquals(role1, new Object());
        assertNotEquals(null, role1);
        assertEquals(role1.hashCode(), role2.hashCode());

        Role role3 = Role.create(UUID.randomUUID(), roleName, permissions);
        assertNotEquals(role1, role3);
    }

    @Test
    void testUpdateRole() {
        Role role = Role.create(roleId, roleName, permissions);
        SecurityName newName = SecurityName.create("ADMIN");

        Role updatedRole = role.update(newName);

        assertEquals(newName, updatedRole.getName());
        assertEquals(permissions, updatedRole.getPermissions());
    }

    @Test
    void testAssignPermissions() {
        Role role = Role.create(roleId, roleName, permissions);
        Permission newPermission = Permission.create(UUID.randomUUID(), SecurityName.create("ADMIN"));

        Role updatedRole = role.assignPermissions(List.of(newPermission));

        assertTrue(updatedRole.getPermissions().contains(newPermission));
        assertEquals(2, updatedRole.getPermissions().size());
    }

    @Test
    void testToString() {
        Role role = Role.create(roleId, roleName, permissions);
        assertEquals("Role{" +
                "id=" + roleId +
                ", name='" + roleName + '\'' +
                ", permissions=" + permissions +
                '}', role.toString());
    }
}
