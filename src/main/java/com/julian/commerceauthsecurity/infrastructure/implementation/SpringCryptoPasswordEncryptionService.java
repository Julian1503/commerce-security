package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import com.julian.commerceshared.security.SpringCrypto;
import org.springframework.stereotype.Service;

@Service
public class SpringCryptoPasswordEncryptionService implements PasswordEncryptionService {

    @Override
    public String encrypt(String textToEncrypt) {
    return SpringCrypto.encrypt(textToEncrypt);
    }

    @Override
    public String decrypt(String encryptedText) {
      return SpringCrypto.decrypt(encryptedText);

    }
    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        String decryptedPassword = SpringCrypto.decrypt(encryptedPassword);
        return rawPassword.equals(decryptedPassword);
    }
}
