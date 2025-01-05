package com.julian.commerceauthsecurity.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.commerceauthsecurity.api.request.role.*;
import com.julian.commerceauthsecurity.api.response.RoleResponse;
import com.julian.commerceauthsecurity.application.command.role.AssignPermissionToRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.CreateRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.DeleteRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.application.query.role.FilteredRolesQuery;
import com.julian.commerceauthsecurity.application.query.role.GetRoleByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceshared.repository.Mapper;
import com.julian.commerceshared.repository.UseCase;
import org.junit.jupiter.api.BeforeEach;
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
import util.RoleBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UseCase<CreateRoleCommand, UUID> createRoleUseCase;

    @MockitoBean
    private UseCase<FilteredRolesQuery, Page<Role>> getAllRolesUseCase;

    @MockitoBean
    private UseCase<GetRoleByIdQuery, Role> getRoleByIdUseCase;

    @MockitoBean
    private UseCase<UpdateRoleCommand, Role> updateRoleUseCase;

    @MockitoBean
    private UseCase<DeleteRoleCommand, Role> deleteRoleUseCase;

    @MockitoBean
    private UseCase<AssignPermissionToRoleCommand, Boolean> assignPermissionToRoleUseCase;

    @MockitoBean
    private Mapper<Role, RoleResponse> roleMapper;

    @MockitoBean
    private PagedResourcesAssembler<RoleResponse> pagedResourcesAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach

    // CREATE
    @Test
    void createRole_ValidRequest_ShouldReturnSuccess() throws Exception {
        CreateRoleRequest request = new CreateRoleRequest("Admin", List.of(UUID.randomUUID()));
        UUID roleId = UUID.randomUUID();

        when(createRoleUseCase.execute(any(CreateRoleCommand.class))).thenReturn(roleId);

        mockMvc.perform(post("/api/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Role was created successfully"))
                .andExpect(jsonPath("$.response").value(roleId.toString()));
    }

    // GET ALL
    @Test
    void getAllRoles_ValidRequest_ShouldReturnRoles() throws Exception {
        GetAllRolesRequest request = new GetAllRolesRequest("Admin", List.of(UUID.randomUUID()), 0, 10, "name", "ASC");
        Role mockRole = RoleBuilder.getBasicRole();
        RoleResponse roleResponse = RoleResponse
                .builder()
                .id(mockRole.getId())
                .name(mockRole.getName().getValue())
                .build();

        when(getAllRolesUseCase.execute(any(FilteredRolesQuery.class))).thenReturn(new PageImpl<>(List.of(mockRole)));
        when(roleMapper.toSource(any(Role.class))).thenReturn(roleResponse);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn(PagedModel.of(List.of(EntityModel.of(roleResponse)), new PagedModel.PageMetadata(1, 0, 1)));

        mockMvc.perform(get("/api/roles/get-all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", mockRole.getName().getValue())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Roles returned successfully"))
                .andExpect(jsonPath("$.response.content[0].name").value(mockRole.getName().getValue()));
    }

    // GET BY ID
    @Test
    void getRoleById_ValidRequest_ShouldReturnRole() throws Exception {
        GetRoleByIdRequest request = new GetRoleByIdRequest(UUID.randomUUID());
        Role mockRole = RoleBuilder.getBasicRole();
        RoleResponse roleResponse = RoleResponse
                .builder()
                .id(mockRole.getId())
                .name(mockRole.getName().getValue())
                .build();

        when(getRoleByIdUseCase.execute(any(GetRoleByIdQuery.class))).thenReturn(mockRole);
        when(roleMapper.toSource(mockRole)).thenReturn(roleResponse);

        mockMvc.perform(get("/api/roles/get-by-id/{roleId}", request.roleId())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Role returned successfully"))
                .andExpect(jsonPath("$.response.name").value(mockRole.getName().getValue()));
    }

    // UPDATE
    @Test
    void updateRole_ValidRequest_ShouldReturnUpdatedRole() throws Exception {
        UpdateRoleRequest request = new UpdateRoleRequest("UpdatedAdmin", UUID.randomUUID());
        Role mockRole = RoleBuilder.getBasicRole();
        RoleResponse roleResponse = RoleResponse
                .builder()
                .id(mockRole.getId())
                .name(mockRole.getName().getValue())
                .build();
        when(updateRoleUseCase.execute(any(UpdateRoleCommand.class))).thenReturn(mockRole);
        when(roleMapper.toSource(mockRole)).thenReturn(roleResponse);

        mockMvc.perform(put("/api/roles/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Role was updated successfully"))
                .andExpect(jsonPath("$.response.name").value(mockRole.getName().getValue()));
    }

    // DELETE
    @Test
    void deleteRole_ValidRequest_ShouldReturnSuccess() throws Exception {
        DeleteRoleRequest request = new DeleteRoleRequest(UUID.randomUUID());
        Role mockRole = RoleBuilder.getBasicRole();
        RoleResponse roleResponse = RoleResponse
                .builder()
                .id(mockRole.getId())
                .name(mockRole.getName().getValue())
                .build();
        when(deleteRoleUseCase.execute(any(DeleteRoleCommand.class))).thenReturn(mockRole);
        when(roleMapper.toSource(mockRole)).thenReturn(roleResponse);

        mockMvc.perform(delete("/api/roles/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Role was deleted successfully"));
    }


    @Test
    void assignPermissionToRole_ValidRequest_ShouldReturnSuccess() throws Exception {
        // Arrange
        UUID roleId = UUID.randomUUID();
        UUID permission1 = UUID.randomUUID();
        UUID permission2 = UUID.randomUUID();

        AssignPermissionsToRoleRequest request = new AssignPermissionsToRoleRequest(
                roleId,
                Arrays.asList(permission1, permission2)
        );

        when(assignPermissionToRoleUseCase.execute(any(AssignPermissionToRoleCommand.class)))
                .thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/api/roles/assign-permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Permission was assigned to role successfully"))
                .andExpect(jsonPath("$.response").value(true));
    }

    @Test
    void assignPermissionToRole_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        AssignPermissionsToRoleRequest request = new AssignPermissionsToRoleRequest(
                null,
                List.of()
        );

        // Act & Assert
        mockMvc.perform(put("/api/roles/assign-permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isBadRequest());
    }
}
