package com.julian.commerceauthsecurity.infrastructure.service;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.service.UserAuthenticationManager;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserRepository;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UserService implements UserAuthenticationManager {
    private final UserRepository userRepository;
    private final Mapper<User, UserEntity> userMapper;

    public UserService(UserRepository userRepository, Mapper<User, UserEntity> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<User> getUserFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .map(userMapper::toDomainModel);
    }
}
