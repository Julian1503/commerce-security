package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record DeleteUserRequest(@NotNull(message = "id is required")
                                @Size(min = 1, message = "id must not be empty") UUID id) {
}
