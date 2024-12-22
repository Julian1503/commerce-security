package com.julian.commerceauthsecurity.infrastructure.repository;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.infrastructure.entity.CustomerEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);

  @Cacheable(value = "usersCache")
  Page<UserEntity>findAll(Specification<UserEntity> spec, Pageable pageable);
}
