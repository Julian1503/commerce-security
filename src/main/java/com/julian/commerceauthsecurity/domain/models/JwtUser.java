package com.julian.commerceauthsecurity.domain.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {
  private static final Long serialVersionUID = 1L;
  private User user;
  private JwtUser(User user) {
    this.user = user;
  }

  public String getUsername() {
    return user.getUsername();
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRoleNames().stream().map(SimpleGrantedAuthority::new).toList();
  }

  public static JwtUser create(User user) {
    return new JwtUser(user);
  }

  public String getPassword() {
    return user.getPassword();
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }
}
