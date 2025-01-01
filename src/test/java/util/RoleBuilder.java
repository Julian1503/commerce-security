package util;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;

import java.util.List;
import java.util.UUID;

public class RoleBuilder {
    public static Role buildRoleWithParameters(String name) {
        return Role.create(UUID.randomUUID(), SecurityName.create(name), List.of(Permission.getBasicPermission()));
    }

    public static Role getBasicRole() {
        return Role.create(UUID.randomUUID(), SecurityName.create("BASIC"), List.of(Permission.getBasicPermission()));
    }

    public static RoleEntity buildRoleEntityWithParameters(String name) {
        return new RoleEntity(UUID.randomUUID(), name, List.of(new PermissionEntity(UUID.randomUUID(), "READ")));
    }

    public static RoleEntity getBasicRoleEntity() {
        return new RoleEntity(UUID.randomUUID(), "BASIC", List.of(new PermissionEntity(UUID.randomUUID(), "READ")));
    }
}
