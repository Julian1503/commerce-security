package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateUserRequest(@NotNull(message = "id is required")
                                @Size(min = 1, message = "id must not be empty")
                                UUID id,
                                @NotEmpty(message = "username is required")
                                @Size(max = 50, message = "Name cannot exceed 50 characters")
                                @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores") String username,
                                @NotEmpty(message = "email is required") String email,
                                @NotEmpty(message = "avatar is required") String avatar) {
}
