package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import com.julian.commerceshared.repository.Mapper;

import java.util.Optional;
import java.util.UUID;

public class UserJpaRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final Mapper<User, UserEntity> userMapper;

    public UserJpaRepositoryAdapter(UserJpaRepository userJpaRepository, Mapper<User, UserEntity> userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
        return userEntity.map(userMapper::toDomainModel);
    }

    @Override
    public UUID save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity userSaved = userJpaRepository.save(userEntity);
        return userSaved.getId();
    }
}
