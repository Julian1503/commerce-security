package com.julian.commerceauthsecurity.api.request.permission;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class GetPermissionByIdRequest {
    private final UUID id;

    public GetPermissionByIdRequest(@NonNull UUID id) {
        this.id = id;
    }
}

