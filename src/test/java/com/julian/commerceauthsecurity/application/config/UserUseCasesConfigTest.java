package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.account.LoginCommand;
import com.julian.commerceauthsecurity.application.command.user.*;
import com.julian.commerceauthsecurity.application.query.user.GetUserByIdQuery;
import com.julian.commerceauthsecurity.application.query.user.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.application.useCase.account.LoginUseCase;
import com.julian.commerceauthsecurity.application.useCase.user.*;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceshared.dto.AuthDto;
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
class UserUseCasesConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void testCreateUserUseCaseBean() {
        UseCase<CreateBasicUserCommand, UUID> useCase = context.getBean("createUserUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(CreateUserUseCase.class, useCase);
    }

    @Test
    void testLoginUseCaseBean() {
        UseCase<LoginCommand, AuthDto> useCase = context.getBean("loginUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(LoginUseCase.class, useCase);
    }

    @Test
    void testChangePasswordUseCaseBean() {
        UseCase<ChangePasswordCommand, Boolean> useCase = context.getBean("changePasswordUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(ChangeUserPasswordUseCase.class, useCase);
    }

    @Test
    void testGetUserByIdUseCaseBean() {
        UseCase<GetUserByIdQuery, User> useCase = context.getBean("getUserByIdUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(GetUserByIdUseCase.class, useCase);
    }

    @Test
    void testGetUsersWithFilterUseCaseBean() {
        UseCase<GetUsersWithFilterQuery, Page<User>> useCase = context.getBean("getUsersWithFilterUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(GetAllUsersUseCase.class, useCase);
    }

    @Test
    void testUpdateUserUseCaseBean() {
        UseCase<UpdateUserCommand, User> useCase = context.getBean("updateUserUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(UpdateUserUseCase.class, useCase);
    }

    @Test
    void testDeleteUserUseCaseBean() {
        UseCase<DeleteUserCommand, User> useCase = context.getBean("deleteUserUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(DeleteUserUseCase.class, useCase);
    }

    @Test
    void testAssignRoleToUserUseCaseBean() {
        UseCase<AssignRoleToUserCommand, Boolean> useCase = context.getBean("assignRoleToUserUseCase", UseCase.class);
        assertNotNull(useCase);
        assertInstanceOf(AssignRoleToUserUseCase.class, useCase);
    }
}
