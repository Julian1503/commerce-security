package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.application.validation.RoleValidation;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceshared.repository.UseCase;

public class UpdateRoleUseCase implements UseCase<UpdateRoleCommand, Role> {

    private final RoleRepository roleRepository;

    public UpdateRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role execute(UpdateRoleCommand command) {
        Role role = roleRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Role roleUpdated = role.update(SecurityName.create(command.name()));
        RoleValidation.validate(roleRepository, roleUpdated);
        roleRepository.save(roleUpdated);
        return roleUpdated;
    }
}
