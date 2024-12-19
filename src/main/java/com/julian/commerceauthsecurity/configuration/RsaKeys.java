package com.julian.commerceauthsecurity.configuration;

import com.julian.commerceauthsecurity.domain.service.RSAKeyProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) implements RSAKeyProvider {

    @Override
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}
