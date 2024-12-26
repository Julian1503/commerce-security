package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.role.AssignPermissionToRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.CreateRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.DeleteRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.application.query.role.FilteredRolesQuery;
import com.julian.commerceauthsecurity.application.query.role.GetRoleByIdQuery;
import com.julian.commerceauthsecurity.application.useCase.role.*;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Configuration
public class RoleUseCasesConfig {
    @Bean
    public UseCase<AssignPermissionToRoleCommand, Boolean> assignPermissionToRoleUseCase(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return new AssignPermissionToRoleUseCase(roleRepository, permissionRepository);
    }

    @Bean
    public UseCase<CreateRoleCommand, UUID> createRoleUseCase(RoleRepository roleRepository) {
        return new CreateRoleUseCase(roleRepository);
    }

    @Bean
    public UseCase<DeleteRoleCommand, Role> deleteRoleUseCase(RoleRepository roleRepository) {
        return new DeleteRoleUseCase(roleRepository);
    }

    @Bean
    public UseCase<FilteredRolesQuery, Page<Role>> getAllRolesUseCase(RoleRepository roleRepository) {
        return new GetAllRolesUseCase(roleRepository);
    }

    @Bean
    public UseCase<GetRoleByIdQuery, Role> getRoleByIdUseCase(RoleRepository roleRepository) {
        return new GetRoleByIdUseCase(roleRepository);
    }

    @Bean
    public UseCase<UpdateRoleCommand, Role> updateRoleUseCase(RoleRepository roleRepository) {
        return new UpdateRoleUseCase(roleRepository);
    }

}
