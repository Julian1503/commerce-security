package com.julian.commerceauthsecurity.domain.service;

import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.function.Consumer;

public interface TokenManager {
    boolean validateToken(String token);
    String generateToken(Authentication authentication, Consumer<Map<String, Object>> claims);

}
