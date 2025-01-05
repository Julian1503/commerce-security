package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.role.AssignPermissionToRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.CreateRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.DeleteRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.application.query.role.FilteredRolesQuery;
import com.julian.commerceauthsecurity.application.query.role.GetRoleByIdQuery;
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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertInstanceOf(AssignPermissionToRoleUseCase.class, useCase);
    }

    @Test
    void testCreateRoleUseCaseBean() {
        UseCase<CreateRoleCommand, UUID> useCase =
                context.getBean("createRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(CreateRoleUseCase.class, useCase);
    }

    @Test
    void testDeleteRoleUseCaseBean() {
        UseCase<DeleteRoleCommand, Role> useCase =
                context.getBean("deleteRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(DeleteRoleUseCase.class, useCase);
    }

    @Test
    void testGetAllRolesUseCaseBean() {
        UseCase<FilteredRolesQuery, Page<Role>> useCase =
                context.getBean("getAllRolesUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(GetAllRolesUseCase.class, useCase);
    }

    @Test
    void testGetRoleByIdUseCaseBean() {
        UseCase<GetRoleByIdQuery, Role> useCase =
                context.getBean("getRoleByIdUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(GetRoleByIdUseCase.class, useCase);
    }

    @Test
    void testUpdateRoleUseCaseBean() {
        UseCase<UpdateRoleCommand, Role> useCase =
                context.getBean("updateRoleUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(UpdateRoleUseCase.class, useCase);
    }
}
