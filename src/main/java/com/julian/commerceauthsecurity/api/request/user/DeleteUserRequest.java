package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteUserRequest {
    @NotNull(message = "id is required")
    private final UUID id;

    public DeleteUserRequest(UUID id) {
        this.id = id;
    }
}
