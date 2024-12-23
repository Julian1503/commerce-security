package com.julian.commerceauthsecurity.domain.repository;

import com.julian.commerceauthsecurity.domain.models.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    UUID save(Permission permission);
    Optional<Permission> findById(UUID id);
    Page<Permission> findAll(String name, Pageable pageable);
    Collection<Permission> findAllByIds(Collection<UUID> uuids);
    void deleteById(UUID id);
}
