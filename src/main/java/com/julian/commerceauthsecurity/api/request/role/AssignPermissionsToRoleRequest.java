package com.julian.commerceauthsecurity.api.request.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.UUID;

public record AssignPermissionsToRoleRequest(@NotNull(message = "id is required")
                                             @Size(min = 1, message = "id must not be empty") UUID roleId,
                                             @NotNull(message = "Permission Ids cannot be null")
                                             @NotEmpty(message = "Should have at least 1 permission") Collection<UUID> permissionIds) {
}
