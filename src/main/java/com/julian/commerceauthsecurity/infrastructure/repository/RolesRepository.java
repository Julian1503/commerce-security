package com.julian.commerceauthsecurity.infrastructure.repository;

import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<RoleEntity, UUID> {
  List<RoleEntity> findAllByNameIn(List<String> names);
}
