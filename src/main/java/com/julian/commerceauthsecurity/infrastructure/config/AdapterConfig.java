package com.julian.commerceauthsecurity.infrastructure.config;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.infrastructure.adapter.PermissionJpaRepositoryAdapter;
import com.julian.commerceauthsecurity.infrastructure.adapter.RoleJpaRepositoryAdapter;
import com.julian.commerceauthsecurity.infrastructure.adapter.UserJpaRepositoryAdapter;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.PermissionJpaRepository;
import com.julian.commerceauthsecurity.infrastructure.repository.RolesJpaRepository;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {
    @Bean
    public PermissionRepository permissionRepository(PermissionJpaRepository permissionJpaRepository, Mapper<Permission, PermissionEntity> permissionMapper) {
        return new PermissionJpaRepositoryAdapter(permissionJpaRepository, permissionMapper);
    }

    @Bean
    public RoleRepository roleRepository(RolesJpaRepository roleJpaRepository, Mapper<Role, RoleEntity> roleMapper) {
        return new RoleJpaRepositoryAdapter(roleJpaRepository, roleMapper);
    }

    @Bean
    public UserRepository userRepository(UserJpaRepository userJpaRepository, Mapper<User, UserEntity> userMapper) {
        return new UserJpaRepositoryAdapter(userJpaRepository, userMapper);
    }
}
