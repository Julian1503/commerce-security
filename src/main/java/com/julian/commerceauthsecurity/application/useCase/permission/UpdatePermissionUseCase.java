package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.UpdatePermissionCommand;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Name;
import com.julian.commerceshared.repository.UseCase;

public class UpdatePermissionUseCase implements UseCase<UpdatePermissionCommand, Permission> {

    private final PermissionRepository permissionRepository;

    public UpdatePermissionUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission execute(UpdatePermissionCommand command) {
        var permission = permissionRepository.findById(command.id());
        if(permission.isEmpty()) {
            throw new IllegalArgumentException("Permission not found");
        }
        var permissionToUpdate = permission.get();
        Permission permissionUpdated = permissionToUpdate.update(Name.create(command.name()));
        permissionRepository.save(permissionToUpdate);
        return permissionUpdated;
    }
}
