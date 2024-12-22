package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.Name;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Permission {
  private final UUID id;
  private final Name name;

  Permission(UUID id, Name name) {
    this.id = id;
    this.name = name;
  }


  public static Permission getBasicPermission() {
    return new Permission(UUID.fromString("BASIC_USER"), Name.create("BASIC_USER"));
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

  public static Permission create(UUID id, Name name) {
    return new Permission(id, name);
  }

}
