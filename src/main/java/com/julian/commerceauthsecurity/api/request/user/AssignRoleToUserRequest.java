package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class AssignRoleToUserRequest {
    @NotNull(message = "userId is required")
    private final UUID userId;
    @NotNull(message = "roleIds is required")
    @NotEmpty(message = "roleIds cannot be empty")
    private final Collection<UUID> roleIds;

    public AssignRoleToUserRequest(UUID userId, Collection<UUID> roleIds) {
        this.userId = userId;
        this.roleIds = roleIds;
    }
}