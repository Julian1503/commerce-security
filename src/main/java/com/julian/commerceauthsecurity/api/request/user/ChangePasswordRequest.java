package com.julian.commerceauthsecurity.api.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(@NotBlank(message = "Old password is required")
                                    @NotNull(message = "Old password is required") String oldPassword,
                                    @NotBlank(message = "New password is required")
                                    @NotNull(message = "New password is required") String newPassword,
                                    @NotBlank(message = "Username is required")
                                    @NotNull(message = "Username is required") String username) {
}
