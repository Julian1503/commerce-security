package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.PermissionJpaRepository;
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
import util.PermissionBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PermissionJpaRepositoryAdapterTest {

    @InjectMocks
    private PermissionJpaRepositoryAdapter permissionJpaRepositoryAdapter;

    @Mock
    private PermissionJpaRepository permissionJpaRepository;

    @Mock
    private Mapper<Permission, PermissionEntity> permissionMapper;

    private Permission permission;
    private PermissionEntity permissionEntity;
    private UUID permissionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        permission = PermissionBuilder.createPermissionWithParameters("READ");
        permissionEntity = PermissionBuilder.createPermissionEntityWithParameters("READ");
        permissionId = permissionEntity.getId();
    }

    @Test
    void testSave_ReturnsUUID() {
        when(permissionMapper.toSource(any(Permission.class))).thenReturn(permissionEntity);
        when(permissionJpaRepository.save(any(PermissionEntity.class))).thenReturn(permissionEntity);

        UUID result = permissionJpaRepositoryAdapter.save(permission);

        assertNotNull(result);
        assertEquals(permissionId, result);
    }

    @Test
    void testFindById_ReturnsPermission() {
        when(permissionJpaRepository.findById(permissionId)).thenReturn(Optional.of(permissionEntity));
        when(permissionMapper.toTarget(permissionEntity)).thenReturn(permission);

        Optional<Permission> result = permissionJpaRepositoryAdapter.findById(permissionId);

        assertTrue(result.isPresent());
        assertEquals(permission, result.get());
    }

    @Test
    void testFindById_NotFound_ReturnsEmpty() {
        when(permissionJpaRepository.findById(permissionId)).thenReturn(Optional.empty());

        Optional<Permission> result = permissionJpaRepositoryAdapter.findById(permissionId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll_ReturnsPagedPermissions() {
        Page<PermissionEntity> permissionEntityPage = new PageImpl<>(List.of(permissionEntity));
        when(permissionJpaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(permissionEntityPage);
        when(permissionMapper.toTarget(permissionEntity)).thenReturn(permission);

        Page<Permission> result = permissionJpaRepositoryAdapter.findAll("READ", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(permission, result.getContent().get(0));
    }

    @Test
    void testExistsByName_ReturnsTrue() {
        when(permissionJpaRepository.existsByName("READ")) .thenReturn(true);

        boolean result = permissionJpaRepositoryAdapter.existsByName("READ");

        assertTrue(result);
    }

    @Test
    void testExistsByName_ReturnsFalse() {
        when(permissionJpaRepository.existsByName("READ")) .thenReturn(false);

        boolean result = permissionJpaRepositoryAdapter.existsByName("READ");

        assertFalse(result);
    }

    @Test
    void testDeleteById_Success() {
        doNothing().when(permissionJpaRepository).deleteById(permissionId);

        assertDoesNotThrow(() -> permissionJpaRepositoryAdapter.deleteById(permissionId));
    }

    @Test
    void testFindAllByIds_ReturnsPermissions() {
        when(permissionJpaRepository.findAllById(anyCollection())).thenReturn(List.of(permissionEntity));
        when(permissionMapper.toTarget(permissionEntity)).thenReturn(permission);

        Collection<Permission> result = permissionJpaRepositoryAdapter.findAllByIds(List.of(permissionId));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(permission));
    }

    @Test
    void testExistsById_ReturnsTrue() {
        when(permissionJpaRepository.existsById(permissionId)).thenReturn(true);

        boolean result = permissionJpaRepositoryAdapter.existsById(permissionId);

        assertTrue(result);
    }

    @Test
    void testExistsById_ReturnsFalse() {
        when(permissionJpaRepository.existsById(permissionId)).thenReturn(false);

        boolean result = permissionJpaRepositoryAdapter.existsById(permissionId);

        assertFalse(result);
    }
}
