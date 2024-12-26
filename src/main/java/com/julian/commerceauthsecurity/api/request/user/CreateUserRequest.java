package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores")
    private String username;
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Username is required")
    private String password;
}
