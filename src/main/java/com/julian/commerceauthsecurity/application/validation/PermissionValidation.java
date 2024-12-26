package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;

public class PermissionValidation {
    public static void validate(PermissionRepository permissionRepository, Permission permission) {
        if(permissionRepository.existsByName(permission.getName().getValue())) {
            throw new IllegalArgumentException("Permission already exists");
        }
    }
}
