package com.julian.commerceauthsecurity.api.request.role;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetRoleByIdRequest(
        @NotNull(message = "id is required") UUID roleId) {
}
