package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.api.response.UserResponse;
import com.julian.commerceauthsecurity.application.command.user.ChangePasswordCommand;
import com.julian.commerceauthsecurity.application.command.user.CreateBasicUserCommand;
import com.julian.commerceauthsecurity.application.query.user.GetUserByIdQuery;
import com.julian.commerceauthsecurity.application.query.user.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceshared.api.controllers.BaseController;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.repository.Mapper;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private final UseCase<CreateBasicUserCommand, UUID> createUserUseCase;
    private final UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase;
    private final UseCase<GetUserByIdQuery, User> getUserByIdUseCase;
    private final UseCase<GetUsersWithFilterQuery, Collection<User>> getUserWithFilterUseCase;
    private final Mapper<User, UserResponse> userMapper;

    public UserController(UseCase<CreateBasicUserCommand, UUID> createUserUseCase,
                          UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase,
                          UseCase<GetUserByIdQuery, User> getUserByIdUseCase,
                          UseCase<GetUsersWithFilterQuery, Collection<User>> getUserWithFilterUseCase,
                          Mapper<User, UserResponse> userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getUserWithFilterUseCase = getUserWithFilterUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping("/create-basic-user")
    public ResponseEntity<BaseResponse> createBasicUser(@Validated @RequestBody CreateBasicUserCommand createBasicUserCommand) {
        ResponseEntity<BaseResponse> baseResponse;
        try {
            UUID userId = createUserUseCase.execute(createBasicUserCommand);
            baseResponse = createSuccessResponse(userId, "User was created successfully");
        } catch (Exception e) {
            baseResponse = createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return baseResponse;
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<BaseResponse> getUser(@PathVariable UUID userId) {
        ResponseEntity<BaseResponse> baseResponse;
        try {
            GetUserByIdQuery query = new GetUserByIdQuery(userId);
            User user = getUserByIdUseCase.execute(query);
            UserResponse userResponse = userMapper.toSource(user);
            baseResponse = createSuccessResponse(userResponse, "User was found successfully");
        } catch (Exception e) {
            baseResponse = createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return baseResponse;
    }

    @GetMapping("/get-user")
    public ResponseEntity<BaseResponse> getUserWithFilter(@RequestParam(required = false) String username,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) String role,
                                                          @RequestParam(required = false) Boolean active,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate createdAfter,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate createdBefore,
                                                          Pageable pageable
    ) {
        ResponseEntity<BaseResponse> baseResponse;
        try {
            GetUsersWithFilterQuery query = new GetUsersWithFilterQuery(
                    username, email, role, active, createdAfter, createdBefore, pageable
            );
            Collection<User> users = getUserWithFilterUseCase.execute(query);
            Collection<UserResponse> userResponses = users.stream().map(userMapper::toSource).toList();
            baseResponse = createSuccessResponse(userResponses, "User was found successfully");
        } catch (Exception e) {
            baseResponse = createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return baseResponse;
    }

    @PutMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@Validated @RequestBody ChangePasswordCommand changePasswordCommand) {
        ResponseEntity<BaseResponse> baseResponse;
        try {
            boolean isSuccesful = changePasswordUseCase.execute(changePasswordCommand);
            if (isSuccesful) {
                baseResponse = createSuccessResponse(true, "Password was changed successfully");
            } else {
                baseResponse = createErrorResponse("Password was not changed due to an error in your password", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            baseResponse = createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);}
        return baseResponse;
    }
}
