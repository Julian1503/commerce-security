package com.julian.commerceauthsecurity.infrastructure.config;

import com.julian.commerceauthsecurity.domain.service.RSAKeyProvider;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.TokenManager;
import com.julian.commerceauthsecurity.domain.service.UserAuthenticationManager;
import com.julian.commerceauthsecurity.infrastructure.adapter.UserJpaRepositoryAdapter;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.implementation.JwtTokenService;
import com.julian.commerceauthsecurity.infrastructure.implementation.LoadUserDetailService;
import com.julian.commerceauthsecurity.infrastructure.implementation.UserService;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.julian.commerceauthsecurity.domain.service.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public TokenManager tokenManager(UserAuthenticationManager userAuthenticationManager, JwtEncoder jwtEncoder, RSAKeyProvider rsaKeyProvider) {
        return new JwtTokenService(userAuthenticationManager, jwtEncoder, rsaKeyProvider);
    }

    @Bean
    public UserAuthenticationManager userAuthenticationManager(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public UserRepository userRepository(UserJpaRepository userJpaRepository, Mapper<User, UserEntity> userMapper) {
        return new UserJpaRepositoryAdapter(userJpaRepository, userMapper);
    }

    @Bean
    public UserDetailsService userDetailsService(UserJpaRepository userRepository) {
        return new LoadUserDetailService(userRepository);
    }
}
