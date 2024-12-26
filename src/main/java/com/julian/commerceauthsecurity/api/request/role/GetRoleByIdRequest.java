package com.julian.commerceauthsecurity.api.request.role;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GetRoleByIdRequest {
    private final UUID roleId;

    public GetRoleByIdRequest(UUID roleId) {
        this.roleId = roleId;
    }
}
