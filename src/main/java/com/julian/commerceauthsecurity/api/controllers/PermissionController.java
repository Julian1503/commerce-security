package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.api.request.permission.*;
import com.julian.commerceauthsecurity.api.response.PermissionResponse;
import com.julian.commerceauthsecurity.application.command.permission.CreatePermissionCommand;
import com.julian.commerceauthsecurity.application.command.permission.DeletePermissionCommand;
import com.julian.commerceauthsecurity.application.command.permission.UpdatePermissionCommand;
import com.julian.commerceauthsecurity.application.query.permission.FilteredPermissionQuery;
import com.julian.commerceauthsecurity.application.query.permission.GetPermissionByIdQuery;
import com.julian.commerceauthsecurity.domain.models.Permission;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController extends BaseController {
    private final UseCase<CreatePermissionCommand, UUID> createPermissionUseCase;
    private final UseCase<FilteredPermissionQuery, Page<Permission>> getAllPermissionUseCase;
    private final UseCase<GetPermissionByIdQuery, Permission> getPermissionByIdUseCase;
    private final UseCase<UpdatePermissionCommand, Permission> updatePermissionUseCase;
    private final UseCase<DeletePermissionCommand, Permission> deletePermissionUseCase;
    private final Mapper<Permission, PermissionResponse> permissionMapper;

    public PermissionController(UseCase<CreatePermissionCommand, UUID> createPermissionUseCase, UseCase<FilteredPermissionQuery, Page<Permission>> getAllPermissionUseCase, UseCase<GetPermissionByIdQuery, Permission> getPermissionByIdUseCase, UseCase<UpdatePermissionCommand, Permission> updatePermissionUseCase, UseCase<DeletePermissionCommand, Permission> deletePermissionUseCase, Mapper<Permission, PermissionResponse> permissionMapper) {
        this.createPermissionUseCase = createPermissionUseCase;
        this.getAllPermissionUseCase = getAllPermissionUseCase;
        this.getPermissionByIdUseCase = getPermissionByIdUseCase;
        this.updatePermissionUseCase = updatePermissionUseCase;
        this.deletePermissionUseCase = deletePermissionUseCase;
        this.permissionMapper = permissionMapper;
    }

    @PreAuthorize("hasPermission('CREATE_PERMISSION')")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        CreatePermissionCommand createPermissionCommand = new CreatePermissionCommand(request.getName());
        UUID permissionId = createPermissionUseCase.execute(createPermissionCommand);
        return createSuccessResponse(permissionId, "Permission was created successfully");
    }

    @PreAuthorize("hasPermission('GET_ALL_PERMISSIONS')")
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAllPermissions(@Valid GetAllPermissionsRequest getAllPermissionsRequest, PagedResourcesAssembler<PermissionResponse> assembler) {
        FilteredPermissionQuery filteredPermissionQuery = new FilteredPermissionQuery(getAllPermissionsRequest.getName(), getAllPermissionsRequest.getPageable());
        Page<Permission> permissions = getAllPermissionUseCase.execute(filteredPermissionQuery);
        PagedModel<EntityModel<PermissionResponse>> response = assembler.toModel(permissions.map(permissionMapper::toSource));
        return createSuccessResponse(response, "Permissions returned successfully");
    }

    @PreAuthorize("hasPermission('GET_PERMISSION_BY_ID')")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<BaseResponse> getPermissionById(@Valid GetPermissionByIdRequest getPermissionByIdRequest) {
        GetPermissionByIdQuery getPermissionByIdQuery = new GetPermissionByIdQuery(getPermissionByIdRequest.getId());
        Permission permission = getPermissionByIdUseCase.execute(getPermissionByIdQuery);
        return createSuccessResponse(permissionMapper.toSource(permission), "Permission returned successfully");
    }

    @PreAuthorize("hasPermission('UPDATE_PERMISSION')")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse> updatePermission(@Valid @RequestBody UpdatePermissionRequest updatePermissionRequest) {
        UpdatePermissionCommand updatePermissionCommand = new UpdatePermissionCommand(updatePermissionRequest.getId(), updatePermissionRequest.getName());
        Permission permission = updatePermissionUseCase.execute(updatePermissionCommand);
        return createSuccessResponse(permission, "Permission was updated successfully");
    }

    @PreAuthorize("hasPermission('DELETE_PERMISSION')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deletePermission(@RequestParam  DeletePermisssionRequest deletePermissionRequest) {
        DeletePermissionCommand deletePermissionCommand = new DeletePermissionCommand(deletePermissionRequest.getId());
        Permission permission = deletePermissionUseCase.execute(deletePermissionCommand);
        return createSuccessResponse(permissionMapper.toSource(permission), "Permission was deleted successfully");
    }
}
