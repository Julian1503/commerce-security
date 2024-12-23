package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.query.permission.FilteredPermissionQuery;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.data.domain.Page;

import java.util.Collection;

public class GetAllPermissionUseCase implements UseCase<FilteredPermissionQuery, Page<Permission>> {
    private final PermissionRepository permissionRepository;

    public GetAllPermissionUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Page<Permission> execute(FilteredPermissionQuery query) {
        return permissionRepository.findAll(
                query.name(),
                query.pagination()
        );
    }
}
