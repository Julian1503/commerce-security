package com.julian.commerceauthsecurity.api.mapper;

import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.api.response.RoleResponse;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceshared.repository.Mapper;

import java.util.stream.Collectors;

public class RoleResponseMapper implements Mapper<Role, RoleResponse> {

    private final Mapper<Permission, PermissionResponse> permissionResponseMapper;

    public RoleResponseMapper(Mapper<Permission, PermissionResponse> permissionResponseMapper) {
        this.permissionResponseMapper = permissionResponseMapper;
    }

    @Override
    public RoleResponse toSource(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName().getValue())
                .permissions(role.getPermissions().stream().map(permissionResponseMapper::toSource).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Role toTarget(RoleResponse roleResponse) {
        return Role.create(roleResponse.getId(), SecurityName.create(roleResponse.getName()), roleResponse.getPermissions().stream().map(permissionResponseMapper::toTarget).collect(Collectors.toList()));
    }
}
