package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.RolesJpaRepository;
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
import util.RoleBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RoleJpaRepositoryAdapterTest {

    @InjectMocks
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @Mock
    private RolesJpaRepository rolesJpaRepository;

    @Mock
    private Mapper<Role, RoleEntity> roleMapper;

    private Role role;
    private RoleEntity roleEntity;
    private UUID roleId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = RoleBuilder.getBasicRole();
        roleEntity = RoleBuilder.getBasicRoleEntity();
        roleId = roleEntity.getId();
    }

    @Test
    void testSave_ReturnsUUID() {
        when(roleMapper.toSource(any(Role.class))).thenReturn(roleEntity);
        when(rolesJpaRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        UUID result = roleJpaRepositoryAdapter.save(role);

        assertNotNull(result);
        assertEquals(roleId, result);
    }

    @Test
    void testSave_NullRole_ThrowsException() {
        assertThrows(NullPointerException.class, () -> roleJpaRepositoryAdapter.save(null));
    }

    @Test
    void testFindById_ReturnsRole() {
        when(rolesJpaRepository.findById(roleId)).thenReturn(Optional.of(roleEntity));
        when(roleMapper.toTarget(roleEntity)).thenReturn(role);

        Optional<Role> result = roleJpaRepositoryAdapter.findById(roleId);

        assertTrue(result.isPresent());
        assertEquals(role, result.get());
    }

    @Test
    void testFindById_NotFound_ReturnsEmpty() {
        when(rolesJpaRepository.findById(roleId)).thenReturn(Optional.empty());

        Optional<Role> result = roleJpaRepositoryAdapter.findById(roleId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindFiltered_ReturnsPagedRoles() {
        Page<RoleEntity> roleEntityPage = new PageImpl<>(List.of(roleEntity));
        when(rolesJpaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(roleEntityPage);
        when(roleMapper.toTarget(roleEntity)).thenReturn(role);

        Page<Role> result = roleJpaRepositoryAdapter.findFiltered(role.getName().getValue(), List.of(roleId), Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(role, result.getContent().get(0));
    }

    @Test
    void testFindFiltered_EmptyResults() {
        Page<RoleEntity> roleEntityPage = new PageImpl<>(Collections.emptyList());
        when(rolesJpaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(roleEntityPage);

        Page<Role> result = roleJpaRepositoryAdapter.findFiltered(role.getName().getValue(), List.of(roleId), Pageable.unpaged());

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testExistsByName_ReturnsTrue() {
        when(rolesJpaRepository.existsByName(role.getName().getValue())).thenReturn(true);

        boolean result = roleJpaRepositoryAdapter.existsByName(role.getName().getValue());

        assertTrue(result);
    }

    @Test
    void testExistsByName_ReturnsFalse() {
        when(rolesJpaRepository.existsByName(role.getName().getValue())).thenReturn(false);

        boolean result = roleJpaRepositoryAdapter.existsByName(role.getName().getValue());

        assertFalse(result);
    }

    @Test
    void testDeleteById_Success() {
        doNothing().when(rolesJpaRepository).deleteById(roleId);

        assertDoesNotThrow(() -> roleJpaRepositoryAdapter.deleteById(roleId));
    }

    @Test
    void testFindAllByIds_ReturnsRoles() {
        when(rolesJpaRepository.findAllById(anyCollection())).thenReturn(List.of(roleEntity));
        when(roleMapper.toTarget(roleEntity)).thenReturn(role);

        Collection<Role> result = roleJpaRepositoryAdapter.findAllByIds(List.of(roleId));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(role));
    }

    @Test
    void testFindAllByIds_EmptyList_ReturnsEmpty() {
        when(rolesJpaRepository.findAllById(anyCollection())).thenReturn(Collections.emptyList());

        Collection<Role> result = roleJpaRepositoryAdapter.findAllByIds(Collections.emptyList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testExistsById_ReturnsTrue() {
        when(rolesJpaRepository.existsById(roleId)).thenReturn(true);

        boolean result = roleJpaRepositoryAdapter.existsById(roleId);

        assertTrue(result);
    }

    @Test
    void testDeleteById_NullId_DoesNotThrowException() {
        assertDoesNotThrow(() -> roleJpaRepositoryAdapter.deleteById(null));
    }

    @Test
    void testExistsById_ReturnsFalse() {
        when(rolesJpaRepository.existsById(roleId)).thenReturn(false);

        boolean result = roleJpaRepositoryAdapter.existsById(roleId);

        assertFalse(result);
    }
}
