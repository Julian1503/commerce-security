package com.julian.commerceauthsecurity.api.mapper;

import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceshared.repository.Mapper;

public class PermissionResponseMapper implements Mapper<Permission, PermissionResponse> {
    @Override
    public PermissionResponse toSource(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName().getValue())
                .build();
    }

    @Override
    public Permission toTarget(PermissionResponse permissionResponse) {
        return Permission.create(permissionResponse.getId(), SecurityName.create(permissionResponse.getName()));
    }

}
