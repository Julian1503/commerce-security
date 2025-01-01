package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserEntity> {

    private final Mapper<Role, RoleEntity> roleMapper;

    public UserMapper(Mapper<Role, RoleEntity> roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserEntity toSource(User user) {
        if (user == null) throw new IllegalArgumentException("UserMapper.toSource: User Model cannot be null");
        UserEntity entity = new UserEntity();
        entity.setId(user.getUserId());
        entity.setAvatar(user.getAvatar().getValue());
        entity.setUsername(user.getUsername().getValue());
        entity.setEmail(user.getEmail().getValue());
        entity.setPassword(user.getPassword().getValue());
        entity.setRoles(user.getRoles().stream().map(roleMapper::toSource).collect(Collectors.toList()));
        return entity;
    }

    public User toTarget(UserEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("UserMapper.toTarget: User Entity cannot be null");
        }

        var roles = entity.getRoles().stream().map(roleMapper::toTarget).collect(Collectors.toList());



        return User.create(
                entity.getId(),
                Avatar.create(entity.getAvatar()),
                Username.create(entity.getUsername()),
                Password.create(entity.getPassword()),
                Email.create(entity.getEmail()),
                roles
        );
    }
}
