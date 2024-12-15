package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.api.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
  protected ResponseEntity<BaseResponse> createSuccessResponse(Object response, String message) {
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setResponse(response);
    baseResponse.setStatus(200);
    baseResponse.setMessage(message);
    return ResponseEntity.ok(baseResponse);
  }

  protected ResponseEntity<BaseResponse> createErrorResponse(String errorMessage) {
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    baseResponse.setSuccess(false);
    baseResponse.setMessage(errorMessage);
    return ResponseEntity.ok(baseResponse);
  }
}
