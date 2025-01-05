package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.api.request.role.*;
import com.julian.commerceauthsecurity.api.response.RoleResponse;
import com.julian.commerceauthsecurity.application.command.role.AssignPermissionToRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.CreateRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.DeleteRoleCommand;
import com.julian.commerceauthsecurity.application.command.role.UpdateRoleCommand;
import com.julian.commerceauthsecurity.application.query.role.FilteredRolesQuery;
import com.julian.commerceauthsecurity.application.query.role.GetRoleByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceshared.api.controllers.BaseController;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.repository.Mapper;
import com.julian.commerceshared.repository.UseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController {
    private final UseCase<AssignPermissionToRoleCommand, Boolean> assignPermissionToRoleUseCase;
    private final UseCase<CreateRoleCommand, UUID> createRoleUseCase;
    private final UseCase<FilteredRolesQuery, Page<Role>> getAllRolesUseCase;
    private final UseCase<GetRoleByIdQuery, Role> getRoleByIdUseCase;
    private final UseCase<UpdateRoleCommand, Role> updateRoleUseCase;
    private final UseCase<DeleteRoleCommand, Role> deleteRoleUseCase;
    private final Mapper<Role, RoleResponse> roleMapper;

    public RoleController(UseCase<AssignPermissionToRoleCommand, Boolean> assignPermissionToRoleUseCase, UseCase<CreateRoleCommand, UUID> createRoleUseCase, UseCase<FilteredRolesQuery, Page<Role>> getAllRolesUseCase, UseCase<GetRoleByIdQuery, Role> getRoleByIdUseCase, UseCase<UpdateRoleCommand, Role> updateRoleUseCase, UseCase<DeleteRoleCommand, Role> deleteRoleUseCase, Mapper<Role, RoleResponse> roleMapper) {
        this.assignPermissionToRoleUseCase = assignPermissionToRoleUseCase;
        this.createRoleUseCase = createRoleUseCase;
        this.getAllRolesUseCase = getAllRolesUseCase;
        this.getRoleByIdUseCase = getRoleByIdUseCase;
        this.updateRoleUseCase = updateRoleUseCase;
        this.deleteRoleUseCase = deleteRoleUseCase;
        this.roleMapper = roleMapper;
    }

    @PreAuthorize("hasPermission('CREATE_ROLE')")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        CreateRoleCommand createRoleCommand = new CreateRoleCommand(request.name(), request.permissionIds());
        UUID roleId = createRoleUseCase.execute(createRoleCommand);
        return createSuccessResponse(roleId, "Role was created successfully");
    }

    @PreAuthorize("hasPermission('GET_ALL_ROLES')")
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAllRoles(@Valid @ModelAttribute  GetAllRolesRequest request,
                                                    PagedResourcesAssembler<RoleResponse> assembler) {
        FilteredRolesQuery filteredRolesQuery = new FilteredRolesQuery(request.getName(), request.getPermissionIds(), request.getPageable());
        Page<Role> roles = getAllRolesUseCase.execute(filteredRolesQuery);
        PagedModel<EntityModel<RoleResponse>> rolesPaged = assembler.toModel(roles.map(roleMapper::toSource));
        return createSuccessResponse(rolesPaged, "Roles returned successfully");
    }

    @PreAuthorize("hasPermission('GET_ROLE_BY_ID')")
    @GetMapping("/get-by-id/{roleId}")
    public ResponseEntity<BaseResponse> getRoleById(@Validated GetRoleByIdRequest request) {
        GetRoleByIdQuery getRoleByIdQuery = new GetRoleByIdQuery(request.roleId());
        Role role = getRoleByIdUseCase.execute(getRoleByIdQuery);
        return createSuccessResponse(roleMapper.toSource(role), "Role returned successfully");
    }

    @PreAuthorize("hasPermission('ASSIGN_PERMISSION_TO_ROLE')")
    @PutMapping("/assign-permission")
    public ResponseEntity<BaseResponse> assignPermissionToRole(@Validated @RequestBody AssignPermissionsToRoleRequest request) {
        AssignPermissionToRoleCommand command = new AssignPermissionToRoleCommand(request.roleId(), request.permissionIds());
        Boolean result = assignPermissionToRoleUseCase.execute(command);
        return createSuccessResponse(result, "Permission was assigned to role successfully");
    }

    @PreAuthorize("hasPermission('UPDATE_ROLE')")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse> updateRole(@Validated @RequestBody UpdateRoleRequest request) {
        UpdateRoleCommand command = new UpdateRoleCommand(request.roleId(), request.name());
        Role role = updateRoleUseCase.execute(command);
        return createSuccessResponse(roleMapper.toSource(role), "Role was updated successfully");
    }

    @PreAuthorize("hasPermission('DELETE_ROLE')")
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteRole(@Validated @RequestBody DeleteRoleRequest request) {
        DeleteRoleCommand command = new DeleteRoleCommand(request.id());
        Role role = deleteRoleUseCase.execute(command);
        return createSuccessResponse(roleMapper.toSource(role), "Role was deleted successfully");
    }
}
