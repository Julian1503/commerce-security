package com.julian.commerceauthsecurity.domain.models;

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
  private final Collection<Role> roles;
  private final boolean active = true;

  User(UUID userId, Avatar avatar, Username username, Password password, Email email, Collection<Role> roles) {
    this.userId = userId;
    this.avatar = avatar;
    this.password = password;
    this.username = username;
    this.email = email;
    this.roles = new ArrayList<>(roles);
  }

  public void changePassword(String encryptedPassword) {
    this.password = Password.create(encryptedPassword);
  }

  public static User create(UUID userId, Avatar avatar, Username username, Password password, Email email, Collection<Role> roles) {
    if(roles == null || roles.isEmpty()) {
      throw new IllegalArgumentException("Roles cannot be null or empty");
    }
    return new User(userId, avatar, username, password, email, roles);
  }

  public User update(Avatar avatar, Username username, Email email) {
    return new User(this.userId, avatar, username, this.password, email, this.roles);
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
    Collection<Role> totalRoles = new ArrayList<>(this.roles);
    totalRoles.addAll(roles);
    return new User(this.userId, this.avatar, this.username, this.password, this.email, totalRoles);
  }
}
