package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateUserRequest {
    @NotNull(message = "id is required")
    private final UUID id;
    @NotEmpty(message = "username is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores")
    private final String username;
    @NotEmpty(message = "email is required")
    private final String email;
    @NotEmpty(message = "avatar is required")
    private final String avatar;

    public UpdateUserRequest(UUID id, String username, String email, String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
    }
}
