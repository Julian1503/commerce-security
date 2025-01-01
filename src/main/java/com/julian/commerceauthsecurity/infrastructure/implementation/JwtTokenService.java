package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.julian.commerceauthsecurity.domain.service.RSAKeyProvider;
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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.util.Objects.hash;

@Service
public class JwtTokenService implements TokenManager {

    private static final Log LOGGER = LogFactory.getLog(JwtTokenService.class);
    private final UserAuthenticationManager userAuthenticationManager;
    private final JwtEncoder encoder;
    private final RSAKeyProvider rsaKeys;

    public JwtTokenService(UserAuthenticationManager userAuthenticationManager, JwtEncoder encoder, RSAKeyProvider rsaKeys) {
        this.userAuthenticationManager = userAuthenticationManager;
        this.encoder = encoder;
        this.rsaKeys = rsaKeys;
    }


    @Override
    public boolean validateToken(String token) {
        boolean isValid = false;
        DecodedJWT jwt = this.getClaimsFromToken(token);
        if (jwt != null) {
            isValid = true;
        }

        return isValid;
    }

    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        User user = userAuthenticationManager.getUserFromAuthentication(authentication)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scid", user.getUserId())
                .claim("scope", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .claim("username", user.getUsername().getValue())
                .claim("email_hash", hash(user.getEmail().getValue()))
                .claim("jti", UUID.randomUUID().toString());

        return encoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
    }

    @Override
    public DecodedJWT verifyToken(String token) {
        DecodedJWT jwt = null;

        try {
            JWTVerifier verifier = JWT.require(Algorithm.RSA256(rsaKeys.getPublicKey(), rsaKeys.getPrivateKey())).withIssuer(new String[]{"self"}).build();
            jwt = verifier.verify(token);
        } catch (Exception var4) {
            LOGGER.error("Error while verifying authentication token.", var4);
        }

        return jwt;
    }

    @Override
    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    private DecodedJWT getClaimsFromToken(String token) {
        DecodedJWT jwt = null;

        try {
            jwt = this.decodeToken(token);
        } catch (Exception var4) {
            LOGGER.trace("Error getting claims from token: " + var4.getMessage());
        }

        return jwt;
    }

}
