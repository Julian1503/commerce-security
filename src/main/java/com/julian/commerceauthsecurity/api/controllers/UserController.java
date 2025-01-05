package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.api.request.user.*;
import com.julian.commerceauthsecurity.api.response.UserResponse;
import com.julian.commerceauthsecurity.application.command.user.*;
import com.julian.commerceauthsecurity.application.query.user.GetUserByIdQuery;
import com.julian.commerceauthsecurity.application.query.user.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceshared.api.controllers.BaseController;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.repository.Mapper;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private final UseCase<CreateBasicUserCommand, UUID> createUserUseCase;
    private final UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase;
    private final UseCase<GetUserByIdQuery, User> getUserByIdUseCase;
    private final UseCase<GetUsersWithFilterQuery, Page<User>> getUserWithFilterUseCase;
    private final UseCase<UpdateUserCommand, User> updateUserUseCase;
    private final UseCase<DeleteUserCommand, User> deleteUserUseCase;
    private final UseCase<AssignRoleToUserCommand, Boolean> assignRoleToUserUseCase;
    private final Mapper<User, UserResponse> userMapper;

    public UserController(UseCase<CreateBasicUserCommand, UUID> createUserUseCase,
                          UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase,
                          UseCase<GetUserByIdQuery, User> getUserByIdUseCase,
                          UseCase<GetUsersWithFilterQuery, Page<User>> getUserWithFilterUseCase, UseCase<UpdateUserCommand, User> updateUserUseCase, UseCase<DeleteUserCommand, User> deleteUserUseCase, UseCase<AssignRoleToUserCommand, Boolean> assignRoleToUserUseCase,
                          Mapper<User, UserResponse> userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getUserWithFilterUseCase = getUserWithFilterUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.assignRoleToUserUseCase = assignRoleToUserUseCase;
        this.userMapper = userMapper;
    }
    @PreAuthorize("hasPermission('CREATE_USER')")
    @PostMapping("/create-basic")
    public ResponseEntity<BaseResponse> createBasicUser(@Validated @RequestBody CreateUserRequest createUserRequest) {
        CreateBasicUserCommand createBasicUserCommand = new CreateBasicUserCommand(
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                createUserRequest.getUsername()
        );
        UUID userId = createUserUseCase.execute(createBasicUserCommand);
        return createSuccessResponse(userId, "User was created successfully");
    }

    @PreAuthorize("hasPermission('GET_USER_BY_ID')")
    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<BaseResponse> getUser(@ModelAttribute GetUserByIdRequest request) {
        GetUserByIdQuery query = new GetUserByIdQuery(request.id());
        User user = getUserByIdUseCase.execute(query);
        UserResponse userResponse = userMapper.toSource(user);
        return createSuccessResponse(userResponse, "User was found successfully");
    }

    @PreAuthorize("hasPermission('GET_ALL_USERS')")
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getUserWithFilter(@Validated @ModelAttribute GetUsersWithFilterRequest request,
                                                          PagedResourcesAssembler<UserResponse> assembler
    ) {
        GetUsersWithFilterQuery query = new GetUsersWithFilterQuery(
                request.getUsername(),
                request.getEmail(),
                request.getRole(),
                request.getActive(),
                request.getCreatedAfter(),
                request.getCreatedBefore(),
                request.getPageable()
        );
        Page<User> users = getUserWithFilterUseCase.execute(query);
        PagedModel<EntityModel<UserResponse>> userPagedModel = assembler.toModel(users.map(userMapper::toSource));
        return createSuccessResponse(userPagedModel, "User was found successfully");
    }

    @PreAuthorize("hasPermission('CHANGE_PASSWORD')")
    @PutMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@Validated @RequestBody ChangePasswordRequest changePasswordRequest) {
        ResponseEntity<BaseResponse> baseResponse;
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(
                changePasswordRequest.username(),
                changePasswordRequest.oldPassword(),
                changePasswordRequest.newPassword()
        );
        boolean isSuccesful = changePasswordUseCase.execute(changePasswordCommand);
        if (isSuccesful) {
            baseResponse = createSuccessResponse(true, "Password was changed successfully");
        } else {
            baseResponse = createErrorResponse("Password was not changed due to an error in your password", HttpStatus.BAD_REQUEST);
        }
        return baseResponse;
    }

    @PreAuthorize("hasPermission('UPDATE_USER')")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse> updateUser(@Validated @RequestBody UpdateUserRequest request) {
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(
                request.id(),
                request.username(),
                request.email(),
                request.avatar()
        );
        User userUpdated = updateUserUseCase.execute(updateUserCommand);
        return createSuccessResponse(userMapper.toSource(userUpdated), "User was updated successfully");
    }

    @PreAuthorize("hasPermission('ASSIGN_ROLE_TO_USER')")
    @PutMapping("/assign-role")
    public ResponseEntity<BaseResponse> assignRoleToUser(@Validated @RequestBody AssignRoleToUserRequest request) {
        AssignRoleToUserCommand assignRoleToUserCommand = new AssignRoleToUserCommand(
                request.userId(),
                request.roleIds()
        );
        Boolean assignedCorrectly = assignRoleToUserUseCase.execute(assignRoleToUserCommand);
        return createSuccessResponse(assignedCorrectly, "Role was assigned to user successfully");
    }

    @PreAuthorize("hasPermission('DELETE_USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteUser(@Validated @RequestBody DeleteUserRequest request) {
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand(request.id());
        User userDeleted = deleteUserUseCase.execute(deleteUserCommand);
        return createSuccessResponse(userMapper.toSource(userDeleted), "User was deleted successfully");
    }
}
