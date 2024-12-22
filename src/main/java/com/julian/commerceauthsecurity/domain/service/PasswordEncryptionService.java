package com.julian.commerceauthsecurity.domain.service;

import com.julian.commerceauthsecurity.domain.valueobject.Password;

public interface PasswordEncryptionService {
    String encrypt(String textToEncrypt);
    String decrypt(String encryptedText);
    boolean matches(String rawPassword, String encryptedPassword);
}
