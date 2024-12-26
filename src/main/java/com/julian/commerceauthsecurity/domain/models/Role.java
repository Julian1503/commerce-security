package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import lombok.Getter;

import java.util.*;

@Getter
public class Role {
    private final UUID id;
    private final SecurityName name;
    private final Collection<Permission> permissions;

    Role(UUID id, SecurityName name, Collection<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions != null ? permissions : Collections.emptyList();
    }

    public static Role getBasicRole() {
        return new Role(UUID.fromString("1b952f6c-6edb-47d5-954b-e0f72d5fa4cb"),SecurityName.create("USER"), List.of());
    }

    public boolean hasPermission(String permissionName) {
        return permissions.stream()
                .anyMatch(permission -> permission.getName().sameValueAs(permissionName));
    }

    public Role addPermission(Permission permission) {
        Collection<Permission> updatedPermissions = new ArrayList<>(this.permissions);
        updatedPermissions.add(permission);
        return new Role(this.id, this.name, updatedPermissions);
    }

    public static Collection<Role> getDefaultRoles() {
        return List.of(Role.getBasicRole());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    public static Role create(UUID id, SecurityName name, Collection<Permission> permissions) {
        return new Role(id, name, permissions);
    }

    public Role update(SecurityName name) {
        return new Role(this.id, name, this.permissions);
    }

    public Role assignPermissions(Collection<Permission> existingPermissions) {
        Collection<Permission> permissions = new ArrayList<>(this.permissions);
        permissions.addAll(existingPermissions);
        return new Role(this.id, this.name, permissions);
    }
}
