package com.julian.commerceauthsecurity.api.config;

import com.julian.commerceauthsecurity.api.mapper.PermissionResponseMapper;
import com.julian.commerceauthsecurity.api.mapper.RoleResponseMapper;
import com.julian.commerceauthsecurity.api.mapper.UserResponseMapper;
import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.api.response.RoleResponse;
import com.julian.commerceauthsecurity.api.response.UserResponse;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfigApi {
    @Bean
    public Mapper<User, UserResponse> userResponseMapper() {
        return new UserResponseMapper();
    }

    @Bean
    public Mapper<Permission, PermissionResponse> permissionResponseMapper() {
        return new PermissionResponseMapper();
    }

    @Bean
    public Mapper<Role, RoleResponse> roleResponseMapper(Mapper<Permission, PermissionResponse> permissionResponseMapper) {
        return new RoleResponseMapper(permissionResponseMapper);
    }
}
