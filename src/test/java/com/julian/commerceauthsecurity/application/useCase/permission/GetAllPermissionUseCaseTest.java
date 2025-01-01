package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.query.permission.FilteredPermissionQuery;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import util.PermissionBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllPermissionUseCaseTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private GetAllPermissionUseCase getAllPermissionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldReturnPermissionsPage_WhenCalledWithValidQuery() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Permission> expectedPage = new PageImpl<>(Collections.singletonList(PermissionBuilder.createPermissionWithRandomName()));
        FilteredPermissionQuery query = new FilteredPermissionQuery("TestName", pageable);
        when(permissionRepository.findAll(query.name(), pageable)).thenReturn(expectedPage);

        // Act
        Page<Permission> result = getAllPermissionUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(permissionRepository, times(1)).findAll(query.name(), query.pagination());
    }

    @Test
    void execute_ShouldReturnEmptyPage_WhenNoPermissionsFound() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Permission> emptyPage = Page.empty();
        FilteredPermissionQuery query = new FilteredPermissionQuery("InvalidName", pageable);
        when(permissionRepository.findAll(query.name(), pageable)).thenReturn(emptyPage);

        // Act
        Page<Permission> result = getAllPermissionUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(permissionRepository, times(1)).findAll(query.name(), query.pagination());
    }
}
