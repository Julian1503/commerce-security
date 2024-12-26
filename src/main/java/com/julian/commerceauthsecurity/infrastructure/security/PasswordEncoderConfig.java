package com.julian.commerceauthsecurity.infrastructure.security;

import com.julian.commerceshared.security.SpringCrypto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderConfig implements PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    return SpringCrypto.encrypt(rawPassword.toString());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return rawPassword.equals(SpringCrypto.decrypt(encodedPassword));
  }

  @Override
  public boolean upgradeEncoding(String encodedPassword) {
    return PasswordEncoder.super.upgradeEncoding(encodedPassword);
  }
}
