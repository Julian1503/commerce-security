package com.julian.commerceauthsecurity.domain.service;

import com.julian.commerceauthsecurity.domain.models.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserAuthenticationManager {
    Optional<User> getUserFromAuthentication(Authentication authentication);
}