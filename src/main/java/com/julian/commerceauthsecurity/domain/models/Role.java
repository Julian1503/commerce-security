package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.Name;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
public class Role {
    private final UUID id;
    private final Name name;
    private final List<Permission> permissions;

    Role(UUID id, Name name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions != null ? permissions : Collections.emptyList();
    }

    Role(Name name, List<Permission> permissions) {
        this(null, name, permissions);
    }

    Role(Name name) {
        this(name, Collections.emptyList());
    }

    public static Role getBasicRole() {
        return new Role(UUID.fromString("1b952f6c-6edb-47d5-954b-e0f72d5fa4cb"),Name.create("USER"), List.of());
    }

    public static List<Role> fromStringArray(String[] roles) {
        return Stream.of(roles)
                .map(role -> new Role(Name.create(role)))
                .toList();
    }

    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }

    public boolean hasPermission(String permissionName) {
        return permissions.stream()
                .anyMatch(permission -> permission.getName().sameValueAs(permissionName));
    }

    public Role addPermission(Permission permission) {
        List<Permission> updatedPermissions = new java.util.ArrayList<>(this.permissions);
        updatedPermissions.add(permission);
        return new Role(this.id, this.name, updatedPermissions);
    }

    public static List<Role> getDefaultRoles() {
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

    public static Role create(UUID id, Name name, List<Permission> permissions) {
        return new Role(id, name, permissions);
    }
}
