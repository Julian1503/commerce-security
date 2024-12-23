package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.DeleteRoleCommand;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceshared.repository.UseCase;

import java.util.Optional;

public class DeleteRoleUseCase implements UseCase<DeleteRoleCommand, Role> {

    private final RoleRepository roleRepository;

    public DeleteRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role execute(DeleteRoleCommand command) {
        Optional<Role> role = roleRepository.findById(command.id());
        if (role.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }
        roleRepository.deleteById(command.id());
        return role.get();
    }
}
