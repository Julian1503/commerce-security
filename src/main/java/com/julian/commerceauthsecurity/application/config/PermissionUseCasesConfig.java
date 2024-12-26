package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.permission.CreatePermissionCommand;
import com.julian.commerceauthsecurity.application.command.permission.DeletePermissionCommand;
import com.julian.commerceauthsecurity.application.command.permission.UpdatePermissionCommand;
import com.julian.commerceauthsecurity.application.query.permission.FilteredPermissionQuery;
import com.julian.commerceauthsecurity.application.query.permission.GetPermissionByIdQuery;
import com.julian.commerceauthsecurity.application.useCase.permission.*;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Configuration
public class PermissionUseCasesConfig {
    @Bean
    public UseCase<CreatePermissionCommand, UUID> createPermissionUseCase(PermissionRepository permissionRepository) {
        return new CreatePermissionUseCase(permissionRepository);
    }

    @Bean
    public UseCase<FilteredPermissionQuery, Page<Permission>> getAllPermissionUseCase(PermissionRepository permissionRepository) {
        return new GetAllPermissionUseCase(permissionRepository);
    }

    @Bean
    public UseCase<UpdatePermissionCommand, Permission> updatePermissionUseCase(PermissionRepository permissionRepository) {
        return new UpdatePermissionUseCase(permissionRepository);
    }

    @Bean
    public UseCase<DeletePermissionCommand, Permission> deletePermissionUseCase(PermissionRepository permissionRepository) {
        return new DeletePermissionUseCase(permissionRepository);
    }

    @Bean
    public UseCase<GetPermissionByIdQuery, Permission> getPermissionByIdUseCase(PermissionRepository permissionRepository) {
        return new GetPermissionByIdUseCase(permissionRepository);
    }
}
