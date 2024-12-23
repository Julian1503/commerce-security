package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.specification.PermissionSpecification;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.julian.commerceauthsecurity.infrastructure.repository.PermissionJpaRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PermissionJpaRepositoryAdapter implements PermissionRepository {

    private final PermissionJpaRepository permissionJpaRepository;
    private final Mapper<Permission, PermissionEntity> permissionMapper;

    public PermissionJpaRepositoryAdapter(PermissionJpaRepository permissionJpaRepository, Mapper<Permission, PermissionEntity> permissionMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public UUID save(Permission permission) {
        PermissionEntity entity = permissionMapper.toSource(permission);
        PermissionEntity entitySaved = permissionJpaRepository.save(entity);
        return entitySaved.getId();
    }

    @Override
    public Optional<Permission> findById(UUID id) {
        return permissionJpaRepository.findById(id).map(permissionMapper::toTarget);
    }

    @Override
    public Page<Permission> findAll(String name, Pageable pageable) {
        Specification<PermissionEntity> specification = PermissionSpecification.hasName(name);
        Page<PermissionEntity> permissionEntityPage = permissionJpaRepository.findAll(specification, pageable);
        return permissionEntityPage.map(permissionMapper::toTarget);
    }

    @Override
    public Collection<Permission> findAllByIds(Collection<UUID> uuids) {
        List<PermissionEntity> permissionEntities = permissionJpaRepository.findAllById(uuids);
        return permissionEntities.stream().map(permissionMapper::toTarget).toList();
    }

    @Override
    public void deleteById(UUID id) {
        permissionJpaRepository.deleteById(id);
    }
}
