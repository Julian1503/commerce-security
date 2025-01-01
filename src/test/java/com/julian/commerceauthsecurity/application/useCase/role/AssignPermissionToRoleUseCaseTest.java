package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.AssignPermissionToRoleCommand;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.PermissionBuilder;
import util.RoleBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignPermissionToRoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private AssignPermissionToRoleUseCase assignPermissionToRoleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldAssignPermissions_WhenRoleAndPermissionsExist() {
        List<UUID> permissionIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        Role role = RoleBuilder.getBasicRole();
        AssignPermissionToRoleCommand command = new AssignPermissionToRoleCommand(role.getId(), permissionIds);

        List<Permission> permissions = Arrays.asList(PermissionBuilder.createPermissionWithRandomName(), PermissionBuilder.createPermissionWithRandomName());

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        when(permissionRepository.findAllByIds(permissionIds)).thenReturn(permissions);

        boolean result = assignPermissionToRoleUseCase.execute(command);

        assertTrue(result);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void execute_ShouldThrowException_WhenRoleNotFound() {
        UUID roleId = UUID.randomUUID();
        List<UUID> permissionIds = Collections.singletonList(UUID.randomUUID());
        AssignPermissionToRoleCommand command = new AssignPermissionToRoleCommand(roleId, permissionIds);

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> assignPermissionToRoleUseCase.execute(command));
    }

    @Test
    void execute_ShouldThrowException_WhenPermissionsNotFound() {
        UUID roleId = UUID.randomUUID();
        List<UUID> permissionIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        AssignPermissionToRoleCommand command = new AssignPermissionToRoleCommand(roleId, permissionIds);

        Role role = RoleBuilder.getBasicRole();

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        when(permissionRepository.findAllByIds(permissionIds)).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> assignPermissionToRoleUseCase.execute(command));
    }
}