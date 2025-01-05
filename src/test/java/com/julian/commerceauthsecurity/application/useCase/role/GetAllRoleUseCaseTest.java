package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.query.role.FilteredRolesQuery;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import util.RoleBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllRoleUseCaseTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetAllRolesUseCase getAllRoleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldReturnRolesPage_WhenCalledWithValidQuery() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        UUID permissionId = UUID.randomUUID();
        Role role = RoleBuilder.getBasicRole();
        FilteredRolesQuery query = new FilteredRolesQuery(role.getName().getValue(), List.of(permissionId), pageable);
        Page<Role> expectedPage = new PageImpl<>(Collections.singletonList(role));

        when(roleRepository.findFiltered(query.name(), query.rolesId(), query.pagination())).thenReturn(expectedPage);

        // Act
        Page<Role> result = getAllRoleUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(role.getName().getValue(), result.getContent().get(0).getName().getValue());
        verify(roleRepository, times(1)).findFiltered(role.getName().getValue(), List.of(permissionId), pageable);
    }

    @Test
    void execute_ShouldReturnEmptyPage_WhenNoRolesFound() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        FilteredRolesQuery query = new FilteredRolesQuery("InvalidName", Collections.emptyList(), pageable);
        Page<Role> emptyPage = Page.empty();

        when(roleRepository.findFiltered(query.name(), query.rolesId(), query.pagination())).thenReturn(emptyPage);

        // Act
        Page<Role> result = getAllRoleUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).findFiltered(query.name(), Collections.emptyList(), pageable);
    }

    @Test
    void execute_ShouldHandleNullPermissions() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        FilteredRolesQuery query = new FilteredRolesQuery("TestName", null, pageable);
        Page<Role> emptyPage = Page.empty();

        when(roleRepository.findFiltered(query.name(), query.rolesId(), query.pagination())).thenReturn(emptyPage);

        // Act
        Page<Role> result = getAllRoleUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).findFiltered(query.name(), query.rolesId(), query.pagination());
    }

    @Test
    void execute_ShouldHandleEmptyName() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Role> emptyPage = Page.empty();
        FilteredRolesQuery query = new FilteredRolesQuery("", Collections.emptyList(), pageable);

        when(roleRepository.findFiltered(query.name(), query.rolesId(), query.pagination())).thenReturn(emptyPage);

        // Act
        Page<Role> result = getAllRoleUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).findFiltered(query.name(), query.rolesId(), query.pagination());
    }
}
