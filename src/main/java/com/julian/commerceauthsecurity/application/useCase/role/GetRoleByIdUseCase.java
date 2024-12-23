package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.query.role.GetRoleByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceshared.repository.UseCase;

import java.util.Optional;

public class GetRoleByIdUseCase implements UseCase<GetRoleByIdQuery, Role> {

    private final RoleRepository roleRepository;

    public GetRoleByIdUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role execute(GetRoleByIdQuery command) {
        return roleRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }
}
