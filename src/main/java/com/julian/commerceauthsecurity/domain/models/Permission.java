package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Permission implements GrantedAuthority {
  private final UUID id;
  private final SecurityName name;

  Permission(UUID id, SecurityName name) {
    this.id = id;
    this.name = name;
  }


  public static Permission getBasicPermission() {
    return new Permission(UUID.fromString("6bf50e9c-5f89-49a5-b915-33382c338bfb"), SecurityName.create("READ_USER"));
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Permission that = (Permission) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  public static Permission create(UUID id, SecurityName name) {
    return new Permission(id, name);
  }

    public Permission update(SecurityName name) {
      return new Permission(this.id, name);
    }

  @Override
  public String getAuthority() {
    return name.getValue();
  }

}
