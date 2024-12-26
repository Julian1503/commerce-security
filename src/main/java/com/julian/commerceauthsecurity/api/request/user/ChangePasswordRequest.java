package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotBlank(message = "Old password is required")
    private final String oldPassword;
    @NotBlank(message = "New password is required")
    private final String newPassword;
    @NotBlank(message = "Username is required")
    private final String username;

    public ChangePasswordRequest(String oldPassword, String newPassword, String username) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.username = username;
    }
}
