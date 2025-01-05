package com.julian.commerceauthsecurity.api.request.permission;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetPermissionByIdRequest(
        @NotNull(message = "id is required") UUID id) {
}

