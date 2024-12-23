package com.julian.commerceauthsecurity.application.command.permission;

import java.util.UUID;

public record UpdatePermissionCommand (UUID id, String name) {
}
