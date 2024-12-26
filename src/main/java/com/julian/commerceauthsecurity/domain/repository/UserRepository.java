package com.julian.commerceauthsecurity.domain.repository;

import com.julian.commerceauthsecurity.domain.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    UUID save(User user);
    boolean existsByUsername(String username);
    Optional<User> findById(UUID id);
    Page<User> findAllWithFilter(String username, String email, Collection<String> role, Boolean active, LocalDate createdAfter, LocalDate createdBefore, Pageable pagination);
    void delete(UUID id);
    boolean existsByEmail(String value);
}
