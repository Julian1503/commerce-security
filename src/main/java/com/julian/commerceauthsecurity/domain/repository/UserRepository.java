package com.julian.commerceauthsecurity.domain.repository;

import com.julian.commerceauthsecurity.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    UUID save(User user);
}
