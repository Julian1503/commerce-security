package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.application.command.user.ChangePasswordCommand;
import com.julian.commerceauthsecurity.application.command.user.CreateUserCommand;
import com.julian.commerceshared.api.controllers.BaseController;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

  private final UseCase<CreateUserCommand, UUID> createUserUseCase;
  private final UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase;

  public UserController(UseCase<CreateUserCommand, UUID> createUserUseCase, UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase) {
    this.createUserUseCase = createUserUseCase;
    this.changePasswordUseCase = changePasswordUseCase;
  }

  @PostMapping("/create-basic-user")
  public ResponseEntity<BaseResponse> createBasicUser(@Validated @RequestBody CreateUserCommand createUserCommand) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      UUID userId = createUserUseCase.execute(createUserCommand);
      baseResponse = createSuccessResponse(userId, "User was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/change-password")
  public ResponseEntity<BaseResponse> changePassword(@Validated @RequestBody ChangePasswordCommand changePasswordCommand) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      boolean isSuccesful = changePasswordUseCase.execute(changePasswordCommand);
      if(isSuccesful) {
        baseResponse = createSuccessResponse(true, "Password was changed successfully");
      } else {
        baseResponse = createErrorResponse("Password was not changed due to an error in your password");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }
}
