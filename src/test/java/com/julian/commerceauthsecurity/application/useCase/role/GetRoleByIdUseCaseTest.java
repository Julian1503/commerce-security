package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.query.role.GetRoleByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.RoleBuilder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetRoleByIdUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetRoleByIdUseCase getRoleByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldReturnRole_WhenRoleExists() {
        Role role = RoleBuilder.getBasicRole();
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        Role result = getRoleByIdUseCase.execute(new GetRoleByIdQuery(role.getId()));

        assertNotNull(result);
        assertEquals(role, result);
        verify(roleRepository, times(1)).findById(role.getId());
    }

    @Test
    void execute_ShouldThrowException_WhenRoleDoesNotExist() {
        UUID roleId = UUID.randomUUID();
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> getRoleByIdUseCase.execute(new GetRoleByIdQuery(roleId)));
        verify(roleRepository, times(1)).findById(roleId);
    }
}
