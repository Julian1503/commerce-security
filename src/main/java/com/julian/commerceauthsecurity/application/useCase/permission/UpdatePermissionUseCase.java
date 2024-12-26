package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.UpdatePermissionCommand;
import com.julian.commerceauthsecurity.application.validation.PermissionValidation;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Name;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceshared.repository.UseCase;

public class UpdatePermissionUseCase implements UseCase<UpdatePermissionCommand, Permission> {

    private final PermissionRepository permissionRepository;

    public UpdatePermissionUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission execute(UpdatePermissionCommand command) {
        Permission permission = permissionRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("Permission does not exist"));
        Permission permissionUpdated = permission.update(SecurityName.create(command.name()));
        PermissionValidation.validate(permissionRepository, permissionUpdated);
        permissionRepository.save(permissionUpdated);
        return permissionUpdated;
    }
}
