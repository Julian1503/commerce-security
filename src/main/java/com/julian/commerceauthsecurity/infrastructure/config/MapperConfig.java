package com.julian.commerceauthsecurity.infrastructure.config;

import com.julian.commerceauthsecurity.domain.models.Customer;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.infrastructure.entity.CustomerEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.mapper.CustomerMapper;
import com.julian.commerceauthsecurity.infrastructure.mapper.PermissionMapper;
import com.julian.commerceauthsecurity.infrastructure.mapper.RoleMapper;
import com.julian.commerceauthsecurity.infrastructure.mapper.UserMapper;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public Mapper<Permission, PermissionEntity> permissionMapper() {
        return new PermissionMapper();
    }

    @Bean
    public Mapper<Role, RoleEntity> roleMapper(Mapper<Permission, PermissionEntity> permissionMapper) {
        return new RoleMapper(permissionMapper);
    }

    @Bean
    public Mapper<Customer, CustomerEntity> customerMapper() {
        return new CustomerMapper();
    }

    @Bean
    public Mapper<User, UserEntity> getUserMapper(Mapper<Role, RoleEntity> roleMapper) {
        return new UserMapper(roleMapper);
    }
}
