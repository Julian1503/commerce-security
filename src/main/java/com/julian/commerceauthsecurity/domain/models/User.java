package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.*;

@Getter
public class User extends AbstractAggregateRoot<User> {
  private final UUID userId;
  private final Avatar avatar;
  private final Username username;
  private Password password;
  private final Email email;
  private final List<Role> roles;
  private final boolean active = true;
  private final UUID customerId;

  User(UUID userId, Avatar avatar, Username username, Password password, Email email, List<Role> roles, UUID customerId) {
    this.userId = userId;
    this.avatar = avatar;
    this.username = username;
    this.email = email;
    this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    this.customerId = customerId;
  }

  public void changePassword(String encryptedPassword) {
    this.password = Password.create(encryptedPassword);
  }

  public static User create(UUID userId, Avatar avatar, Username username, Password password, Email email, List<Role> roles, UUID customerId) {
    if(roles == null || roles.isEmpty()) {
      throw new IllegalArgumentException("Roles cannot be null or empty");
    }
    return new User(userId, avatar, username, password, email, roles, customerId);
  }

  public User update(Avatar avatar, Username username, Email email) {
    return new User(this.userId, avatar, username, this.password, email, this.roles, this.customerId);
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

  public User assignRoles(Collection<Role> roles) {
    List<Role> totalRoles = new ArrayList<>(this.roles);
    totalRoles.addAll(roles);
    return new User(this.userId, this.avatar, this.username, this.password, this.email, totalRoles, this.customerId);
  }
}
