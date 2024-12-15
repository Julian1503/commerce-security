package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceshared.repository.Mapper;

public class PermissionMapper implements Mapper<Permission, PermissionEntity> {

    public PermissionEntity toEntity(Permission permission) {
        if (permission == null) throw new IllegalArgumentException("PermissionMapper.toEntity: Permission Model cannot be null");
        PermissionEntity entity = new PermissionEntity();
        entity.setId(permission.getId());
        entity.setName(permission.getName());
        return entity;
    }

    public Permission toDomainModel(PermissionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("PermissionMapper.toDomainModel: Permission Entity cannot be null");
        return Permission.create(entity.getId(), entity.getName());
    }
}
