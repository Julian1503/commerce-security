package com.julian.commerceauthsecurity.infrastructure.service;

import com.julian.commerceauthsecurity.domain.models.JwtUser;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserRepository;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoadUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final Mapper<User, UserEntity> userMapper;

    public LoadUserDetailService(UserRepository userRepository, Mapper<User, UserEntity> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(user -> JwtUser.create(userMapper.toDomainModel(user))).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
