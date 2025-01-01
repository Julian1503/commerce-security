package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.role.*;
import com.julian.commerceauthsecurity.application.query.role.*;
import com.julian.commerceauthsecurity.application.useCase.role.*;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.PermissionRepository;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceshared.repository.UseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleUseCasesConfigTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void testAssignPermissionToRoleUseCaseBean() {
        UseCase<AssignPermissionToRoleCommand, Boolean> useCase =
                context.getBean("assignPermissionToRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertTrue(useCase instanceof AssignPermissionToRoleUseCase);
    }

    @Test
    void testCreateRoleUseCaseBean() {
        UseCase<CreateRoleCommand, UUID> useCase =
                context.getBean("createRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertTrue(useCase instanceof CreateRoleUseCase);
    }

    @Test
    void testDeleteRoleUseCaseBean() {
        UseCase<DeleteRoleCommand, Role> useCase =
                context.getBean("deleteRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertTrue(useCase instanceof DeleteRoleUseCase);
    }

    @Test
    void testGetAllRolesUseCaseBean() {
        UseCase<FilteredRolesQuery, Page<Role>> useCase =
                context.getBean("getAllRolesUseCase", UseCase.class);
        assertNotNull(useCase);
        assertTrue(useCase instanceof GetAllRolesUseCase);
    }

    @Test
    void testGetRoleByIdUseCaseBean() {
        UseCase<GetRoleByIdQuery, Role> useCase =
                context.getBean("getRoleByIdUseCase", UseCase.class);
        assertNotNull(useCase);
        assertTrue(useCase instanceof GetRoleByIdUseCase);
    }

    @Test
    void testUpdateRoleUseCaseBean() {
        UseCase<UpdateRoleCommand, Role> useCase =
                context.getBean("updateRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertTrue(useCase instanceof UpdateRoleUseCase);
    }
}
