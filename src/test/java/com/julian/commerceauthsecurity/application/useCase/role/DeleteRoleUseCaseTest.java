package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.DeleteRoleCommand;
import com.julian.commerceauthsecurity.application.useCase.role.DeleteRoleUseCase;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.RoleBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DeleteRoleUseCaseTest {
    @InjectMocks
    private DeleteRoleUseCase deleteRoleUseCase;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteMethod_RoleEmpty() {
        when(roleRepository.findById(any())).thenReturn(Optional.empty());
        DeleteRoleCommand command = new DeleteRoleCommand(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> deleteRoleUseCase.execute(command));
    }

    @Test
    public void testExecuteMethod_RoleNotEmpty() {
        UUID id = UUID.randomUUID();
        Role role = RoleBuilder.getBasicRole();
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        DeleteRoleCommand command = new DeleteRoleCommand(id);
        var result = deleteRoleUseCase.execute(command);
        assertEquals(result, role);
    }
}
