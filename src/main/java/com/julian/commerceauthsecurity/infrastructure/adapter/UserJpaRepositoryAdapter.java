package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import com.julian.commerceauthsecurity.infrastructure.specification.UserSpecification;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
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
        return userEntity.map(userMapper::toTarget);
    }

    @Override
    public UUID save(User user) {
        UserEntity userEntity = userMapper.toSource(user);
        UserEntity userSaved = userJpaRepository.save(userEntity);
        return userSaved.getId();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(userMapper::toTarget);
    }

    @Override
    public Collection<User> findAllWithFilter(String username, String email, String role, Boolean active, LocalDate createdAfter, LocalDate createdBefore, Pageable pagination) {
        Specification<UserEntity> spec = UserSpecification.Filters(username, email, role, active, createdAfter, createdBefore);
        return userJpaRepository.
                findAll(spec, pagination)
                .stream()
                .map(userMapper::toTarget)
                .toList();
    }
}
