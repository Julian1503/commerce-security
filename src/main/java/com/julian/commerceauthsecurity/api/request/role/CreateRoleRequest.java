package com.julian.commerceauthsecurity.api.request.role;

import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class CreateRoleRequest {
    private final String name;
    private final Collection<UUID> permissionIds;

    public CreateRoleRequest(String name, Collection<UUID> permissionIds) {
        this.name = name;
        this.permissionIds = permissionIds;
    }
}
