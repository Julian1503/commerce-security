package util;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.valueobject.*;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserBuilder {
    public static User getValidUser() {
        return createUserWithParams(
                UUID.randomUUID(),
                Username.create("testuser"),
                Password.create("securePassword123"),
                Email.create("test@example.com")
        );
    }

    public static User createUserWithParams(UUID userId, Username username, Password password, Email email) {
        if (userId == null) throw new IllegalArgumentException("User ID cannot be null");
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        if (password == null) throw new IllegalArgumentException("Password cannot be null");
        if (email == null) throw new IllegalArgumentException("Email cannot be null");

        return User.create(
                userId,
                Avatar.create("avatar-url"),
                username,
                password,
                email,
                List.of(Role.create(UUID.randomUUID(), SecurityName.create("USER"), List.of())),
                UUID.randomUUID()
        );
    }

    public static UserEntity createUserEntityWithParams(UUID userId, String username, String password, String email, Collection<RoleEntity> roles) {
        if (userId == null) throw new IllegalArgumentException("User ID cannot be null");
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        if (password == null) throw new IllegalArgumentException("Password cannot be null");
        if (email == null) throw new IllegalArgumentException("Email cannot be null");

        return new UserEntity(
                userId,
                "avatar-url",
                username,
                password,
                email,
                false,
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                roles,
                null
        );
    }

    public static UserEntity getValidUserEntity() {
        return createUserEntityWithParams(
                UUID.randomUUID(),
                "testuser",
                "securePassword123",
                "ju@do.com",
                List.of(new RoleEntity(UUID.randomUUID(), "USER", List.of()))
        );
    }

}
