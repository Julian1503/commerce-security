package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class User extends AbstractAggregateRoot<User> {
  private final UUID userId;
  private final byte[] avatar;
  private final String username;
  private String password;
  private final String email;
  private final List<Role> roles;
  private final Customer user;

  User(UUID userId, byte[] avatar, String username, String password, String email, List<Role> roles, Customer user) {
    this.userId = userId;
    this.avatar = avatar;
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    this.user = user;
  }

  User(String username, String password, String email, List<Role> roles) {
    this(null, null, username, password, email, roles, null);
  }

  public void changePassword(String newPassword, PasswordEncryptionService encryptionService) {
    this.password = encryptionService.encrypt(newPassword);
  }

  public boolean isPasswordValid(String rawPassword, PasswordEncryptionService encryptionService) {
    return encryptionService.matches(rawPassword, this.password);
  }

  public List<String> getRoleNames() {
    return roles.stream().map(Role::getName).toList();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  public static User create(UUID userId, byte[] avatar, String username, String password, String email, List<Role> roles, Customer user) {
    if(userId == null) {
      throw new IllegalArgumentException("User id cannot be null");
    }
    if(username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    if(password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    if(email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if(roles == null || roles.isEmpty()) {
      throw new IllegalArgumentException("Roles cannot be null or empty");
    }
    return new User(userId, avatar, username, password, email, roles, user);
  }
}
