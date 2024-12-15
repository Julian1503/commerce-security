package com.julian.commerceauthsecurity.domain.service;

public interface PasswordEncryptionService {
    String encrypt(String textToEncrypt);
    String decrypt(String encryptedText);
    boolean matches(String rawPassword, String encryptedPassword);
}
