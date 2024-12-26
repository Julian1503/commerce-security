package com.julian.commerceauthsecurity.api.request.role;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteRoleRequest {
    private final UUID id;

    public DeleteRoleRequest(UUID id) {
        this.id = id;
    }
}
