package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class GetUserByIdRequest {
    @NotNull(message = "id is required")
    private final UUID id;

    public GetUserByIdRequest(UUID id) {
        this.id = id;
    }
}
