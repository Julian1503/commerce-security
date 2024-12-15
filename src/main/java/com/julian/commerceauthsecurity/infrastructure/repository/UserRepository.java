package com.julian.commerceauthsecurity.infrastructure.repository;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}
