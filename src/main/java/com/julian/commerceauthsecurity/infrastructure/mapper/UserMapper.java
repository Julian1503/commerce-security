package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Customer;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.infrastructure.entity.CustomerEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceshared.repository.Mapper;

import java.util.stream.Collectors;

public class UserMapper implements Mapper<User, UserEntity> {

    private final Mapper<Role, RoleEntity> roleMapper;

    public UserMapper(Mapper<Role, RoleEntity> roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserEntity toEntity(User user) {
        if (user == null) throw new IllegalArgumentException("UserMapper.toEntity: User Model cannot be null");
        UserEntity entity = new UserEntity();
        entity.setId(user.getUserId());
        entity.setAvatar(user.getAvatar());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRoles(user.getRoles().stream().map(roleMapper::toEntity).collect(Collectors.toList()));
        return entity;
    }

    public User toDomainModel(UserEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("UserMapper.toDomainModel: User Entity cannot be null");
        }

        var roles = entity.getRoles().stream().map(roleMapper::toDomainModel).collect(Collectors.toList());

        var customerEntity = entity.getCustomer();


        return User.create(
                entity.getId(),
                entity.getAvatar(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                roles,
                customerEntity != null? customerEntity.getId() : null
        );
    }
}
