package com.julian.commerceauthsecurity.domain.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface RSAKeyProvider {
    RSAPrivateKey getPrivateKey();
    RSAPublicKey  getPublicKey();
}
