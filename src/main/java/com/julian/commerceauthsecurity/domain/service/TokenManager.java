package com.julian.commerceauthsecurity.domain.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;

public interface TokenManager {
    boolean validateToken(String token);
    String generateToken(Authentication authentication);
    DecodedJWT  verifyToken(String token);
    DecodedJWT decodeToken(String token);
}
