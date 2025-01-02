package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.AssignRoleToUserCommand;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.RoleBuilder;
import util.UserBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignRoleToUserUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssignRoleToUserUseCase assignRoleToUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldAssignRoles_WhenUserAndRolesExist() {
        List<UUID> roleIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        User user = UserBuilder.getValidUser();
        AssignRoleToUserCommand command = new AssignRoleToUserCommand(user.getUserId(), roleIds);

        List<Role> roles = Arrays.asList(RoleBuilder.getBasicRole(), RoleBuilder.getBasicRole());

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(roleRepository.findAllByIds(roleIds)).thenReturn(roles);

        boolean result = assignRoleToUserUseCase.execute(command);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        List<UUID> roleIds = Collections.singletonList(UUID.randomUUID());
        AssignRoleToUserCommand command = new AssignRoleToUserCommand(userId, roleIds);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> assignRoleToUserUseCase.execute(command));
    }

    @Test
    void execute_ShouldThrowException_WhenRolesIsEmpty() {
        UUID userId = UUID.randomUUID();
        List<UUID> roleIds = new ArrayList<>();
        AssignRoleToUserCommand command = new AssignRoleToUserCommand(userId, roleIds);
        assertThrows(IllegalArgumentException.class, () -> assignRoleToUserUseCase.execute(command));
    }

    @Test
    void execute_ShouldThrowException_WhenUserIsNull() {
        List<UUID> roleIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        AssignRoleToUserCommand command = new AssignRoleToUserCommand(null, roleIds);
        assertThrows(IllegalArgumentException.class, () -> assignRoleToUserUseCase.execute(command));
    }

    @Test
    void execute_ShouldThrowException_WhenRolesNotFound() {
        UUID userId = UUID.randomUUID();
        List<UUID> roleIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        AssignRoleToUserCommand command = new AssignRoleToUserCommand(userId, roleIds);

        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findAllByIds(roleIds)).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> assignRoleToUserUseCase.execute(command));
    }
}
