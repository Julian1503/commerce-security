package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.*;

@Getter
public class User extends AbstractAggregateRoot<User> {
  private final UUID userId;
  private final String avatar;
  private final String username;
  private String password;
  private final String email;
  private final List<Role> roles;
  private final UUID customerId;

  User(UUID userId, String avatar, String username, String password, String email, List<Role> roles, UUID customerId) {
    this.userId = userId;
    this.avatar = avatar;
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    this.customerId = customerId;
  }

  public boolean changePassword(String oldPassword, String newPassword, PasswordEncryptionService encryptionService) {
    if(this.isPasswordValid(oldPassword, encryptionService)) {
      this.password = newPassword;
      return true;
    }
    return false;
  }

  public boolean isPasswordValid(String rawPassword, PasswordEncryptionService encryptionService) {
    return encryptionService.matches(rawPassword, this.password);
  }

  public List<String> getRoleNames() {
    return roles.stream().map(Role::getName).toList();
  }

  public static User create(UUID userId, String avatar, String username, String password, String email, List<Role> roles, UUID customerId) {
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
    return new User(userId, avatar, username, password, email, roles, customerId);
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
}
