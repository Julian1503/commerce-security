package com.julian.commerceauthsecurity.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.commerceauthsecurity.api.request.user.*;
import com.julian.commerceauthsecurity.api.response.UserResponse;
import com.julian.commerceauthsecurity.application.command.user.*;
import com.julian.commerceauthsecurity.application.query.user.GetUserByIdQuery;
import com.julian.commerceauthsecurity.application.query.user.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceshared.repository.Mapper;
import com.julian.commerceshared.repository.UseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import util.UserBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UseCase<CreateBasicUserCommand, UUID> createUserUseCase;

    @MockitoBean
    private UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase;

    @MockitoBean
    private UseCase<GetUserByIdQuery, User> getUserByIdUseCase;

    @MockitoBean
    private UseCase<GetUsersWithFilterQuery, Page<User>> getUserWithFilterUseCase;

    @MockitoBean
    private UseCase<UpdateUserCommand, User> updateUserUseCase;

    @MockitoBean
    private UseCase<DeleteUserCommand, User> deleteUserUseCase;

    @MockitoBean
    private UseCase<AssignRoleToUserCommand, Boolean> assignRoleToUserUseCase;

    @MockitoBean
    private Mapper<User, UserResponse> userMapper;

    @MockitoBean
    private PagedResourcesAssembler<UserResponse> pagedResourcesAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    // CREATE
    @Test
    void createBasicUser_ValidRequest_ShouldReturnSuccess() throws Exception {
        CreateUserRequest request = new CreateUserRequest("testUser", "test@example.com", "Password123!");
        UUID userId = UUID.randomUUID();

        when(createUserUseCase.execute(any(CreateBasicUserCommand.class))).thenReturn(userId);

        mockMvc.perform(post("/api/user/create-basic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User was created successfully"))
                .andExpect(jsonPath("$.response").value(userId.toString()));
    }

    @Test
    void createBasicUser_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        CreateUserRequest request = new CreateUserRequest("", "", "");

        mockMvc.perform(post("/api/user/create-basic")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // GET BY ID
    @Test
    void getUser_ValidRequest_ShouldReturnUser() throws Exception {
        UUID userId = UUID.randomUUID();
        GetUserByIdRequest request = new GetUserByIdRequest(userId);
        User mockUser = UserBuilder.getValidUser();
        UserResponse response = UserResponse.builder()
                .id(mockUser.getUserId())
                .username(mockUser.getUsername().getValue())
                .email(mockUser.getEmail().getValue())
                .build();

        when(getUserByIdUseCase.execute(any(GetUserByIdQuery.class))).thenReturn(mockUser);
        when(userMapper.toSource(mockUser)).thenReturn(response);

        mockMvc.perform(get("/api/user/get-by-id/{userId}", userId)
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User was found successfully"))
                .andExpect(jsonPath("$.response.username").value(mockUser.getUsername().getValue()));
    }

    @Test
    void getUserWithFilter_ValidRequest_ShouldReturnUsers() throws Exception {
        // Arrange
        GetUsersWithFilterRequest request = new GetUsersWithFilterRequest(
                0, 10, "username", "asc",
                "testUser", "test@example.com", List.of("ADMIN"), true,
                LocalDate.now().minusDays(10), LocalDate.now()
        );

        User mockUser = UserBuilder.getValidUser();
        UserResponse response = UserResponse.builder()
                .id(mockUser.getUserId())
                .username(mockUser.getUsername().getValue())
                .email(mockUser.getEmail().getValue())
                .build();

        Page<User> mockPage = new PageImpl<>(Collections.singletonList(mockUser));

        PagedModel<EntityModel<UserResponse>> pagedModel = PagedModel.of(
                Collections.singletonList(EntityModel.of(response)),
                new PagedModel.PageMetadata(10, 0, 1)
        );

        when(getUserWithFilterUseCase.execute(any(GetUsersWithFilterQuery.class))).thenReturn(mockPage);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn(PagedModel.of(List.of(EntityModel.of(response)), new PagedModel.PageMetadata(1, 0, 1)));
        when(userMapper.toSource(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/user/get-all")
                        .param("username", "testUser")
                        .param("email", "test@example.com")
                        .param("role", "ADMIN")
                        .param("active", "true")
                        .param("createdAfter", LocalDate.now().minusDays(10).toString())
                        .param("createdBefore", LocalDate.now().toString())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "username")
                        .param("direction", "asc")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User was found successfully"))
                .andExpect(jsonPath("$.response.content[0].username").value(response.getUsername()));
    }

    @Test
    void assignRoleToUser_ValidRequest_ShouldReturnSuccess() throws Exception {
        // Arrange
        AssignRoleToUserRequest request = new AssignRoleToUserRequest(
                UUID.randomUUID(),
                Collections.singletonList(UUID.randomUUID())
        );

        when(assignRoleToUserUseCase.execute(any(AssignRoleToUserCommand.class))).thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/api/user/assign-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Role was assigned to user successfully"))
                .andExpect(jsonPath("$.response").value(true));
    }

    // UPDATE
    @Test
    void updateUser_ValidRequest_ShouldReturnUpdatedUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest(UUID.randomUUID(), "updatedUser", "updated@example.com", "avatar");
        User mockUser = UserBuilder.getValidUser();
        UserResponse response =  UserResponse.builder()
                .id(mockUser.getUserId())
                .username(mockUser.getUsername().getValue())
                .email(mockUser.getEmail().getValue())
                .build();
        when(updateUserUseCase.execute(any(UpdateUserCommand.class))).thenReturn(mockUser);
        when(userMapper.toSource(mockUser)).thenReturn(response);

        mockMvc.perform(put("/api/user/update")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User was updated successfully"))
                .andExpect(jsonPath("$.response.username").value(mockUser.getUsername().getValue()));
    }

    // DELETE
    @Test
    void deleteUser_ValidRequest_ShouldReturnSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        DeleteUserRequest request = new DeleteUserRequest(userId);
        User mockUser = UserBuilder.getValidUser();
        UserResponse response =  UserResponse.builder()
                .id(mockUser.getUserId())
                .username(mockUser.getUsername().getValue())
                .email(mockUser.getEmail().getValue())
                .build();
        when(deleteUserUseCase.execute(any(DeleteUserCommand.class))).thenReturn(mockUser);
        when(userMapper.toSource(mockUser)).thenReturn(response);

        mockMvc.perform(delete("/api/user/delete", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User was deleted successfully"));
    }

    // CHANGE PASSWORD
    @Test
    void changePassword_ValidRequest_ShouldReturnSuccess() throws Exception {
        ChangePasswordRequest request = new ChangePasswordRequest("testUser", "OldPass123!", "NewPass123!");

        when(changePasswordUseCase.execute(any(ChangePasswordCommand.class))).thenReturn(true);

        mockMvc.perform(put("/api/user/change-password")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password was changed successfully"));
    }

    @Test
    void changePassword_ValidRequest_ShouldReturnNotSuccess() throws Exception {
        ChangePasswordRequest request = new ChangePasswordRequest("testUser", "OldPass123!", "NewPass123!");

        when(changePasswordUseCase.execute(any(ChangePasswordCommand.class))).thenReturn(false);

        mockMvc.perform(put("/api/user/change-password")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password was not changed due to an error in your password"));
    }
}
