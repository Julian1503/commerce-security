package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.application.command.LoginCommand;
import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.dto.AuthDto;
import com.julian.gymapp.model.LoginRequest;
import com.julian.gymapp.service.interfaces.ISecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

  private final ISecurityService securityService;
  private final AuthenticationManager authorizationManager;
  @PostMapping("")
  public ResponseEntity<BaseResponse> token(@RequestBody LoginCommand loginRequest) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      Authentication authentication = authorizationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
      Consumer<Map<String, Object>> claims = securityService.getClaims();
      SecurityContextHolder.getContext().setAuthentication(authentication);
      AuthDto authDto = new AuthDto(securityService.generateToken(authentication, claims), loginRequest.username());
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
