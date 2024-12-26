package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.query.role.FilteredRolesQuery;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.data.domain.Page;


public class GetAllRolesUseCase implements UseCase<FilteredRolesQuery, Page<Role>> {

    private final RoleRepository roleRepository;

    public GetAllRolesUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> execute(FilteredRolesQuery command) {
        return roleRepository.findFiltered(
                command.name(),
                command.rolesId(),
                command.pagination()
        );
    }
}
