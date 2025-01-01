package com.julian.commerceauthsecurity.application.useCase.permission;

import com.julian.commerceauthsecurity.application.command.permission.CreatePermissionCommand;
import com.julian.commerceauthsecurity.application.validation.PermissionValidation;
import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreatePermissionUseCaseTest {
    @InjectMocks
    private CreatePermissionUseCase createPermissionUseCase;
    @Mock
    private PermissionRepository permissionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteMethod() {
        CreatePermissionCommand command = new CreatePermissionCommand("mockName");
        UUID id = UUID.randomUUID();
        when(permissionRepository.save(any())).thenReturn(id);
        try(var mockedValidation = mockStatic(PermissionValidation.class)) {
            mockedValidation.when(() -> PermissionValidation.validate(any(PermissionRepository.class), any(Permission.class)))
                    .thenAnswer(invocation -> null);
            var result = createPermissionUseCase.execute(command);
            assertEquals(id, result);
            ArgumentCaptor<Permission> captor = ArgumentCaptor.forClass(Permission.class);
            verify(permissionRepository).save(captor.capture());
            Permission capturedPermission = captor.getValue();
            assertEquals(command.name().toUpperCase(), capturedPermission.getName().getValue());
            verifyNoMoreInteractions(permissionRepository);
        }
    }
}
