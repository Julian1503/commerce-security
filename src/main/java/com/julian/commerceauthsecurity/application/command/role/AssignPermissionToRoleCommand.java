package com.julian.commerceauthsecurity.application.command.role;

import java.util.Collection;
import java.util.UUID;

public record AssignPermissionToRoleCommand(UUID roleId, Collection<UUID> permissionIds) {

}
