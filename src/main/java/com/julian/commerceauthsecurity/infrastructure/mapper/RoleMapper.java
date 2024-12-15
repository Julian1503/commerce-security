package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceshared.repository.Mapper;

public class RoleMapper implements Mapper<Role, RoleEntity> {

    private final Mapper<Permission, PermissionEntity> permissionMapper;

    public RoleMapper(Mapper<Permission, PermissionEntity> permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public RoleEntity toEntity(Role role) {
        if (role == null) throw new IllegalArgumentException("RoleMapper.toEntity: Role Model cannot be null");
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        return entity;
    }

    public Role toDomainModel(RoleEntity entity) {
        if (entity == null) throw new IllegalArgumentException("RoleMapper.toDomainModel: Role Entity cannot be null");
        var permission = entity.getPermissions().stream().map(permissionMapper::toDomainModel).toList();
        return Role.create(entity.getId(), entity.getName(), permission);
    }
}
