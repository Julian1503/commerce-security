package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.permission.CreatePermissionCommand;
import com.julian.commerceauthsecurity.application.useCase.permission.CreatePermissionUseCase;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceshared.repository.UseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PermissionUseCasesConfigTest {

    @Autowired
    private ApplicationContext context;

    @MockitoBean
    private PermissionRepository permissionRepository;

    @InjectMocks
    private CreatePermissionUseCase createPermissionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePermissionUseCaseBean() {
        // Act
        UseCase<CreatePermissionCommand, UUID> useCase = context.getBean("createPermissionUseCase", UseCase.class);

        // Assert
        assertNotNull(useCase);
        assertInstanceOf(CreatePermissionUseCase.class, useCase);
    }

    @Test
    void testGetAllPermissionUseCaseBean() {
        // Act
        var useCase = context.getBean("getAllPermissionUseCase", UseCase.class);

        // Assert
        assertNotNull(useCase);
    }

    @Test
    void testUpdatePermissionUseCaseBean() {
        // Act
        var useCase = context.getBean("updatePermissionUseCase", UseCase.class);

        // Assert
        assertNotNull(useCase);
    }

    @Test
    void testDeletePermissionUseCaseBean() {
        // Act
        var useCase = context.getBean("deletePermissionUseCase", UseCase.class);

        // Assert
        assertNotNull(useCase);
    }

    @Test
    void testGetPermissionByIdUseCaseBean() {
        // Act
        var useCase = context.getBean("getPermissionByIdUseCase", UseCase.class);

        // Assert
        assertNotNull(useCase);
    }
}