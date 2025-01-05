package com.julian.commerceauthsecurity.api.request.role;

import jakarta.validation.constraints.*;

import java.util.Collection;
import java.util.UUID;

public record CreateRoleRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores") String name,
        @NotNull(message = "Permission Ids cannot be null")
        @NotEmpty(message = "Should have at least 1 permission") Collection<UUID> permissionIds) {
}
