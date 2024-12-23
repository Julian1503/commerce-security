package com.julian.commerceauthsecurity.application.command.role;

import java.util.Collection;
import java.util.UUID;

public record CreateRoleCommand (String name, Collection<UUID> permissions) {
}
