package com.julian.commerceauthsecurity.infrastructure.repository;

import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);

  Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);
}
