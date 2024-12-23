package com.julian.commerceauthsecurity.application.command.user;

import java.util.UUID;

public record UpdateUserCommand (UUID id, String username, String email, String avatar) {
}
