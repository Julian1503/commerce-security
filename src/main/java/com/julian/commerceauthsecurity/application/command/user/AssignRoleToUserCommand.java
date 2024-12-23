package com.julian.commerceauthsecurity.application.command.user;

import java.util.Collection;
import java.util.UUID;

public record AssignRoleToUserCommand(UUID userId, Collection<UUID> roleIds) {
}
