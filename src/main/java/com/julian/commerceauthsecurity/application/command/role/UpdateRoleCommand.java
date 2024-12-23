package com.julian.commerceauthsecurity.application.command.role;

import java.util.UUID;

public record UpdateRoleCommand (UUID id, String name) {
}
