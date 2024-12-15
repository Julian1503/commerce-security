package com.julian.commerceauthsecurity.infrastructure.service;

import com.auth0.jwt.JWT;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.service.TokenManager;
import com.julian.commerceauthsecurity.domain.service.UserAuthenticationManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Consumer;

public class JwtTokenService implements TokenManager {

    private static final Log LOGGER = LogFactory.getLog(JwtTokenService.class);
    private final UserAuthenticationManager userAuthenticationManager;
    private final JwtEncoder encoder;

    public JwtTokenService(UserAuthenticationManager userAuthenticationManager, JwtEncoder encoder) {
        this.userAuthenticationManager = userAuthenticationManager;
        this.encoder = encoder;
    }


    @Override
    public boolean validateToken(String token) {
        Boolean isValid = false;
        DecodedJWT jwt = this.getClaimsFromToken(token);
        if (jwt != null) {
            isValid = true;
        }

        return isValid;
    }

    @Override
    public String generateToken(Authentication authentication, Consumer<Map<String, Object>> claims) {
        Instant now = Instant.now();
        User user = userAuthenticationManager.getUserFromAuthentication(authentication)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .claim("email", user.getEmail());

        addClaimIfNotNull(claimsBuilder, "scid", user.getUser() != null ? user.getUser().getId() : null);
        addClaimIfNotNull(claimsBuilder, "user", user.getUser() != null ? user.getUserId() : null);
        addClaimIfNotNull(claimsBuilder, "fullName", user.getUser() != null
                ? user.getUser().getName() + " " + user.getUser().getLastName()
                : null);

        claims.accept(claimsBuilder.build().getClaims());

        return encoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();

    }

    private void addClaimIfNotNull(JwtClaimsSet.Builder builder, String key, Object value) {
        if (value != null) {
            builder.claim(key, value);
        }
    }

    private DecodedJWT getClaimsFromToken(String token) {
        DecodedJWT jwt = null;

        try {
            jwt = this.decode(token);
        } catch (Exception var4) {
            LOGGER.trace("Error getting claims from token: " + var4.getMessage());
        }

        return jwt;
    }

    private DecodedJWT decode(String authToken) {
        return JWT.decode(authToken);
    }

}
