package com.julian.commerceauthsecurity.api.request.role;

import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class AssignPermissionsToRoleRequest {
    private final UUID roleId;
    private final Collection<UUID> permissionIds;

    public AssignPermissionsToRoleRequest(UUID roleId, Collection<UUID> permissionIds) {
        this.roleId = roleId;
        this.permissionIds = permissionIds;
    }
}
