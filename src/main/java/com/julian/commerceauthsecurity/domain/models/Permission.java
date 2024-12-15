package com.julian.commerceauthsecurity.domain.models;

import java.util.Objects;
import java.util.UUID;

public class Permission {
  private final UUID id;
  private final String name;

  // Constructor para crear un permiso con un ID (ej., al cargar desde la base de datos)
  Permission(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  // Constructor para crear un permiso sin ID (ej., al crear nuevos permisos)
  public Permission(String name) {
    this(null, name);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  // Lógica adicional relacionada con permisos podría ir aquí

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

  public static Permission create(UUID id, String name) {
    return new Permission(id, name);
  }
}
