package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.query.permission.GetPermissionByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceshared.repository.UseCase;
import jakarta.persistence.EntityNotFoundException;

public class GetPermissionByIdUseCase implements UseCase<GetPermissionByIdQuery, Permission> {

    private final PermissionRepository permissionRepository;

    public GetPermissionByIdUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission execute(GetPermissionByIdQuery query) {
        return permissionRepository.findById(query.id()).orElseThrow(() -> new EntityNotFoundException("Permission was not found"));
    }
}
