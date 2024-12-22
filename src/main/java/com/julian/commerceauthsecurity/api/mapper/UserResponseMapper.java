package com.julian.commerceauthsecurity.api.mapper;

import com.julian.commerceauthsecurity.api.response.UserResponse;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceshared.repository.Mapper;

public class UserResponseMapper implements Mapper<User, UserResponse> {
    @Override
    public UserResponse toSource(User domain) {
        return UserResponse.builder()
                .id(domain.getUserId())
                .username(domain.getUsername().getValue())
                .email(domain.getEmail().getValue())
                .build();
    }

    @Override
    public User toTarget(UserResponse response) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
