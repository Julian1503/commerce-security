package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Name;
import com.julian.commerceshared.repository.UseCase;

public class UpdateRoleUseCase implements UseCase<UpdateRoleCommand, Role> {

    private final RoleRepository roleRepository;

    public UpdateRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role execute(UpdateRoleCommand command) {
        Role role = roleRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Role roleUpdated = role.update(Name.create(command.name()));
        roleRepository.save(role);
        return roleUpdated;
    }
}
