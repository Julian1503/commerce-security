package com.julian.commerceauthsecurity.api.request.permission;

import lombok.Getter;

@Getter
public class CreatePermissionRequest {
    private final String name;

    public CreatePermissionRequest(String name) {
        this.name = name;
    }
}
