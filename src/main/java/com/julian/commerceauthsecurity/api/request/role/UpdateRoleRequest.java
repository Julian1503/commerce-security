package com.julian.commerceauthsecurity.api.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateRoleRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores") String name,
        @NotNull(message = "id is required") UUID roleId) {
}
