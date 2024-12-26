package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.CreateRoleCommand;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Name;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceshared.repository.UseCase;

import java.util.UUID;

public class CreateRoleUseCase implements UseCase<CreateRoleCommand, UUID> {

    private final RoleRepository roleRepository;

    public CreateRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UUID execute(CreateRoleCommand command) {
        Role role = Role.create(null, SecurityName.create(command.name()), command.permissions().stream().map(p -> Permission.create(p, null)).toList());
        return roleRepository.save(role);
    }
}
