package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements Mapper<Role, RoleEntity> {

    private final Mapper<Permission, PermissionEntity> permissionMapper;

    public RoleMapper(Mapper<Permission, PermissionEntity> permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public RoleEntity toSource(Role role) {
        if (role == null) throw new IllegalArgumentException("RoleMapper.toSource: Role Model cannot be null");
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName().getValue());
        return entity;
    }

    public Role toTarget(RoleEntity entity) {
        if (entity == null) throw new IllegalArgumentException("RoleMapper.toTarget: Role Entity cannot be null");
        var permission = entity.getPermissions().stream().map(permissionMapper::toTarget).toList();
        return Role.create(entity.getId(), SecurityName.create(entity.getName()), permission);
    }
}
