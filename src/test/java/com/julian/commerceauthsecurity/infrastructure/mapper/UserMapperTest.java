package com.julian.commerceauthsecurity.infrastructure.mapper;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.valueobject.*;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserMapperTest {

    private UserMapper mapper;
    private RoleMapper roleMapper;

    @BeforeEach
    void setUp() {
        roleMapper = mock(RoleMapper.class);
        mapper = new UserMapper(roleMapper);
    }

    @Test
    void testToSource_ValidUser_ReturnsUserEntity() {
        Role role = Role.create(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"), SecurityName.create("ADMIN"), List.of());
        User user = User.create(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                Avatar.create("avatar.png"),
                Username.create("johndoe"),
                Password.create("password123"),
                Email.create("johndoe@example.com"),
                List.of(role)
        );

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        roleEntity.setName("ADMIN");

        when(roleMapper.toSource(role)).thenReturn(roleEntity);

        UserEntity entity = mapper.toSource(user);

        assertNotNull(entity);
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"), entity.getId());
        assertEquals("avatar.png", entity.getAvatar());
        assertEquals("johndoe", entity.getUsername());
        assertEquals("password123", entity.getPassword());
        assertEquals("johndoe@example.com", entity.getEmail());
    }

    @Test
    void testToSource_NullUser_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.toSource(null));
        assertEquals("UserMapper.toSource: User Model cannot be null", thrown.getMessage());
    }

    @Test
    void testToTarget_ValidUserEntity_ReturnsUser() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        roleEntity.setName("ADMIN");

        UserEntity entity = new UserEntity();
        entity.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        entity.setAvatar("avatar.png");
        entity.setUsername("johndoe");
        entity.setEmail("johndoe@example.com");
        entity.setPassword("password123");
        entity.setRoles(List.of(roleEntity));

        Role role = Role.create(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"), SecurityName.create("ADMIN"), List.of());
        when(roleMapper.toTarget(roleEntity)).thenReturn(role);

        User user = mapper.toTarget(entity);

        assertNotNull(user);
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"), user.getUserId());
        assertEquals("avatar.png", user.getAvatar().getValue());
        assertEquals("johndoe", user.getUsername().getValue());
        assertEquals("password123", user.getPassword().getValue());
        assertEquals("johndoe@example.com", user.getEmail().getValue());
    }

    @Test
    void testToTarget_NullUserEntity_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.toTarget(null));
        assertEquals("UserMapper.toTarget: User Entity cannot be null", thrown.getMessage());
    }
}
