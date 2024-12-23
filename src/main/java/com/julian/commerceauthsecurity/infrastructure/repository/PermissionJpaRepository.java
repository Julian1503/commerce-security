package com.julian.commerceauthsecurity.infrastructure.repository;

import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    @Cacheable("permissions")
    Page<PermissionEntity> findAll(Specification<PermissionEntity> specification, Pageable pageable);
}
