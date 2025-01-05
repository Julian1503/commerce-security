package com.julian.commerceauthsecurity.application.useCase.role;

import com.julian.commerceauthsecurity.application.command.role.CreateRoleCommand;
import com.julian.commerceauthsecurity.application.validation.RoleValidation;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateRoleUseCaseTest {
    @InjectMocks
    private CreateRoleUseCase createRoleUseCase;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteMethod() {
        List<UUID> permissionList = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );
        CreateRoleCommand command = new CreateRoleCommand("mockName", permissionList);
        UUID id = UUID.randomUUID();
        when(roleRepository.save(any())).thenReturn(id);
        try(var mockedValidation = mockStatic(RoleValidation.class)) {
            mockedValidation.when(() -> RoleValidation.validate(any(RoleRepository.class), any(Role.class)))
                    .thenAnswer(invocation -> null);
            var result = createRoleUseCase.execute(command);
            assertEquals(id, result);
            ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
            verify(roleRepository).save(captor.capture());
            Role capturedRole = captor.getValue();
            assertEquals("MOCKNAME", capturedRole.getName().getValue());
            assertEquals(2, capturedRole.getPermissions().size());
            verifyNoMoreInteractions(roleRepository);
        }
    }
}
