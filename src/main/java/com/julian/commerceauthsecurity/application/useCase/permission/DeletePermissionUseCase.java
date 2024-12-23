package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.DeletePermissionCommand;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceshared.repository.UseCase;

import java.util.Optional;

public class DeletePermissionUseCase implements UseCase<DeletePermissionCommand, Permission> {

    private final PermissionRepository permissionRepository;

    public DeletePermissionUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission execute(DeletePermissionCommand command) {
        Optional<Permission> permission = permissionRepository.findById(command.id());
        if (permission.isEmpty()) {
            throw new IllegalArgumentException("Permission not found");
        }
        permissionRepository.deleteById(command.id());
        return permission.get();
    }
}
