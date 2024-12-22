package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.valueobject.Name;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceshared.repository.Mapper;

public class PermissionMapper implements Mapper<Permission, PermissionEntity> {

    public PermissionEntity toSource(Permission permission) {
        if (permission == null) throw new IllegalArgumentException("PermissionMapper.toSource: Permission Model cannot be null");
        PermissionEntity entity = new PermissionEntity();
        entity.setId(permission.getId());
        entity.setName(permission.getName().getValue());
        return entity;
    }

    public Permission toTarget(PermissionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("PermissionMapper.toTarget: Permission Entity cannot be null");
        return Permission.create(entity.getId(), Name.create(entity.getName()));
    }
}
