package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.UUID;

public record AssignRoleToUserRequest(@NotNull(message = "id is required")
                                      @NotBlank(message = "id must not be empty") UUID userId,
                                      @NotNull(message = "roleIds is required")
                                      @NotEmpty(message = "roleIds cannot be empty") Collection<UUID> roleIds) {
}