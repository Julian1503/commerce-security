package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.AssignPermissionToRoleCommand;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceshared.repository.UseCase;

import java.util.Collection;

public class AssignPermissionToRoleUseCase implements UseCase<AssignPermissionToRoleCommand, Boolean> {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public AssignPermissionToRoleUseCase(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Boolean execute(AssignPermissionToRoleCommand command) {
        if(command.permissionIds().isEmpty() || command.roleId() == null) {
            throw new IllegalArgumentException("Permission or Role cannot be null");
        }

        Role role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        Collection<Permission> existingPermissions = permissionRepository.findAllByIds(command.permissionIds());

        if (existingPermissions.size() != command.permissionIds().size()) {
            throw new IllegalArgumentException("One or more permissions do not exist.");
        }

        Role roleUpdated = role.assignPermissions(existingPermissions);

        roleRepository.save(roleUpdated);
        return true;
    }
}
