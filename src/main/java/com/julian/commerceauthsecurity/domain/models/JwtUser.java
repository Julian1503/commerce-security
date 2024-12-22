package com.julian.commerceauthsecurity.domain.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

public class JwtUser implements UserDetails {
  @Serial
  private static final long serialVersionUID = 1L;
  private final UserDetails user;

  private JwtUser(UserDetails user) {
      this.user = user;
  }

    public static JwtUser create(UserDetails user) {
        return new JwtUser(user);
    }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
