package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import com.julian.commerceshared.repository.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import util.UserBuilder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserJpaRepositoryAdapterTest {

    @InjectMocks
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private Mapper<User, UserEntity> userMapper;

    private User user;
    private UserEntity userEntity;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = UserBuilder.getValidUser();
        userEntity = UserBuilder.getValidUserEntity();
        userId = userEntity.getId();
    }

    @Test
    void testSave_ReturnsUUID() {
        when(userMapper.toSource(any(User.class))).thenReturn(userEntity);
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UUID result = userJpaRepositoryAdapter.save(user);

        assertNotNull(result);
        assertEquals(userId, result);
    }

    @Test
    void testFindById_ReturnsUser() {
        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toTarget(userEntity)).thenReturn(user);

        Optional<User> result = userJpaRepositoryAdapter.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindById_NotFound_ReturnsEmpty() {
        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userJpaRepositoryAdapter.findById(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByUsername_ReturnsUser() {
        when(userJpaRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(userMapper.toTarget(userEntity)).thenReturn(user);

        Optional<User> result = userJpaRepositoryAdapter.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByUsername_NotFound_ReturnsEmpty() {
        when(userJpaRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        Optional<User> result = userJpaRepositoryAdapter.findByUsername("testuser");

        assertTrue(result.isEmpty());
    }

    @Test
    void testExistsByUsername_ReturnsTrue() {
        when(userJpaRepository.existsByUsername("testuser")).thenReturn(true);

        boolean result = userJpaRepositoryAdapter.existsByUsername("testuser");

        assertTrue(result);
    }

    @Test
    void testExistsByUsername_ReturnsFalse() {
        when(userJpaRepository.existsByUsername("testuser")).thenReturn(false);

        boolean result = userJpaRepositoryAdapter.existsByUsername("testuser");

        assertFalse(result);
    }

    @Test
    void testFindAllWithFilter_ReturnsPagedUsers() {
        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity));
        when(userJpaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userEntityPage);
        when(userMapper.toTarget(userEntity)).thenReturn(user);

        Page<User> result = userJpaRepositoryAdapter.findAllWithFilter(
                "testuser", "test@email.com", Collections.emptyList(),
                true, LocalDate.now().minusDays(1), LocalDate.now(), Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(user, result.getContent().get(0));
    }

    @Test
    void testExistsByEmail_ReturnsTrue() {
        when(userJpaRepository.existsByEmail("test@email.com")).thenReturn(true);

        boolean result = userJpaRepositoryAdapter.existsByEmail("test@email.com");

        assertTrue(result);
    }

    @Test
    void testExistsByEmail_ReturnsFalse() {
        when(userJpaRepository.existsByEmail("test@email.com")).thenReturn(false);

        boolean result = userJpaRepositoryAdapter.existsByEmail("test@email.com");

        assertFalse(result);
    }

    @Test
    void testDelete_Success() {
        doNothing().when(userJpaRepository).deleteById(userId);

        assertDoesNotThrow(() -> userJpaRepositoryAdapter.delete(userId));
    }
}
