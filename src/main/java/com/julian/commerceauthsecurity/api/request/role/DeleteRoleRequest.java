package com.julian.commerceauthsecurity.api.request.role;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteRoleRequest(
        @NotNull(message = "id is required") UUID id) {
}
