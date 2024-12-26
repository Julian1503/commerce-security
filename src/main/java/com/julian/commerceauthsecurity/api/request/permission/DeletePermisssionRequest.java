package com.julian.commerceauthsecurity.api.request.permission;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeletePermisssionRequest {
    private final UUID id;

    public DeletePermisssionRequest(UUID id) {
        this.id = id;
    }
}
