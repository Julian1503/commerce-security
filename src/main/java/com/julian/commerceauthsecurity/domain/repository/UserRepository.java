package com.julian.commerceauthsecurity.domain.repository;

import com.julian.commerceauthsecurity.application.query.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;

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
    Collection<User> findAllWithFilter(String username, String email, String role, Boolean active, LocalDate createdAfter, LocalDate createdBefore, Pageable pagination);
}
