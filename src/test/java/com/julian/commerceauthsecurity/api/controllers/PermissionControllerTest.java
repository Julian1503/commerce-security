package com.julian.commerceauthsecurity.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.commerceauthsecurity.api.request.permission.*;
import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.application.command.permission.CreatePermissionCommand;
import com.julian.commerceauthsecurity.application.command.permission.DeletePermissionCommand;
import com.julian.commerceauthsecurity.application.command.permission.UpdatePermissionCommand;
import com.julian.commerceauthsecurity.application.query.permission.FilteredPermissionQuery;
import com.julian.commerceauthsecurity.application.query.permission.GetPermissionByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Permission;
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
import util.PermissionBuilder;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PermissionController.class)
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UseCase<CreatePermissionCommand, UUID> createPermissionUseCase;

    @MockitoBean
    private UseCase<FilteredPermissionQuery, Page<Permission>> getAllPermissionUseCase;

    @MockitoBean
    private UseCase<GetPermissionByIdQuery, Permission> getPermissionByIdUseCase;

    @MockitoBean
    private UseCase<UpdatePermissionCommand, Permission> updatePermissionUseCase;

    @MockitoBean
    private UseCase<DeletePermissionCommand, Permission> deletePermissionUseCase;

    @MockitoBean
    private Mapper<Permission, PermissionResponse> permissionMapper;

    @MockitoBean
    private PagedResourcesAssembler<PermissionResponse> pagedResourcesAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    // CREATE
    @Test
    void createPermission_ValidRequest_ShouldReturnSuccess() throws Exception {
        // Arrange
        CreatePermissionRequest request = new CreatePermissionRequest("READ");
        UUID permissionId = UUID.randomUUID();

        when(createPermissionUseCase.execute(any(CreatePermissionCommand.class))).thenReturn(permissionId);

        // Act & Assert
        mockMvc.perform(post("/api/permissions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Permission was created successfully"))
                .andExpect(jsonPath("$.response").value(permissionId.toString()));
    }

    // GET ALL
    @Test
    void getAllPermissions_ValidRequest_ShouldReturnPermissions() throws Exception {
        // Arrange
        GetAllPermissionsRequest request = new GetAllPermissionsRequest(0, 10, "name", "ASC", "READ");
        Permission mockPermission = PermissionBuilder.createPermissionWithRandomName();
        PermissionResponse response = PermissionResponse
                .builder()
                .id(mockPermission.getId())
                .name(mockPermission.getName().getValue())
                .build();
        Page<Permission> permissions = new PageImpl<>(Collections.singletonList(mockPermission));

        when(getAllPermissionUseCase.execute(any(FilteredPermissionQuery.class))).thenReturn(permissions);
        when(permissionMapper.toSource(any(Permission.class))).thenReturn(response);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn(
                PagedModel.of(Collections.singletonList(EntityModel.of(response)), new PagedModel.PageMetadata(1, 0, 1))
        );

        // Act & Assert
        mockMvc.perform(get("/api/permissions/get-all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", mockPermission.getName().getValue())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Permissions returned successfully"))
                .andExpect(jsonPath("$.response.content[0].name").value(mockPermission.getName().getValue()));
    }

    // GET BY ID
    @Test
    void getPermissionById_ValidRequest_ShouldReturnPermission() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        GetPermissionByIdRequest request = new GetPermissionByIdRequest(id);
        Permission mockPermission = PermissionBuilder.createPermissionWithRandomName();
        PermissionResponse response = PermissionResponse
                .builder()
                .id(mockPermission.getId())
                .name(mockPermission.getName().getValue())
                .build();

        when(getPermissionByIdUseCase.execute(any(GetPermissionByIdQuery.class))).thenReturn(mockPermission);
        when(permissionMapper.toSource(mockPermission)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/permissions/get-by-id/{id}", request.id())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Permission returned successfully"))
                .andExpect(jsonPath("$.response.name").value(mockPermission.getName().getValue()));
    }

    // UPDATE
    @Test
    void updatePermission_ValidRequest_ShouldReturnUpdatedPermission() throws Exception {
        // Arrange
        UpdatePermissionRequest request = new UpdatePermissionRequest(UUID.randomUUID(), "PERMISSION");
        Permission mockPermission = PermissionBuilder.createPermissionWithRandomName();
        PermissionResponse response = PermissionResponse
                .builder()
                .id(mockPermission.getId())
                .name(mockPermission.getName().getValue())
                .build();

        when(updatePermissionUseCase.execute(any())).thenReturn(mockPermission);
        when(permissionMapper.toSource(mockPermission)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/permissions/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Permission was updated successfully"))
                .andExpect(jsonPath("$.response.name").value(mockPermission.getName().getValue()));
    }

    // DELETE
    @Test
    void deletePermission_ValidRequest_ShouldReturnSuccess() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        DeletePermissionRequest request = new DeletePermissionRequest(id);
        Permission mockPermission = PermissionBuilder.createPermissionWithRandomName();
        PermissionResponse response = PermissionResponse
                .builder()
                .id(mockPermission.getId())
                .name(mockPermission.getName().getValue())
                .build();

        when(deletePermissionUseCase.execute(any())).thenReturn(mockPermission);
        when(permissionMapper.toSource(mockPermission)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(delete("/api/permissions/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Permission was deleted successfully"));
    }
}
