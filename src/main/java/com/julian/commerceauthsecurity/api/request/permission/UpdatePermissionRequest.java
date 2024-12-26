package com.julian.commerceauthsecurity.api.request.permission;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePermissionRequest {
    private final UUID id;
    private final String name;

    public UpdatePermissionRequest(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
