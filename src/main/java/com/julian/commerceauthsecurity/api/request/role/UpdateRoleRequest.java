package com.julian.commerceauthsecurity.api.request.role;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateRoleRequest {
    private final String name;
    private final UUID roleId;

    public UpdateRoleRequest(String name, UUID roleId) {
        this.name = name;
        this.roleId = roleId;
    }
}
