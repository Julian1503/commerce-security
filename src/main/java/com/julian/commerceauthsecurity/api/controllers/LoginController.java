package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.application.command.account.LoginCommand;
import com.julian.commerceauthsecurity.application.useCase.account.LoginUseCase;
import com.julian.commerceshared.api.controllers.BaseController;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.dto.AuthDto;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController extends BaseController {

  private final UseCase<LoginCommand, AuthDto> loginUseCase;

  public LoginController(UseCase<LoginCommand, AuthDto> loginUseCase) {
    this.loginUseCase = loginUseCase;
  }

  @PostMapping("")
  public ResponseEntity<BaseResponse> token(@RequestBody LoginCommand loginRequest) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      AuthDto authDto = loginUseCase.execute(loginRequest);
      baseResponse.setResponse(authDto);
      baseResponse.setMessage("Authenticated successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getLocalizedMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }
}
