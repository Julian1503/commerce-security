package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.CreatePermissionCommand;
import com.julian.commerceauthsecurity.application.validation.PermissionValidation;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceshared.repository.UseCase;

import java.util.UUID;

public class CreatePermissionUseCase implements UseCase<CreatePermissionCommand, UUID> {

    private final PermissionRepository permissionRepository;

    public CreatePermissionUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UUID execute(CreatePermissionCommand command) {
        Permission permission = Permission.create(null, SecurityName.create(command.name()));
        PermissionValidation.validate(permissionRepository, permission);
        return permissionRepository.save(permission);
    }
}
