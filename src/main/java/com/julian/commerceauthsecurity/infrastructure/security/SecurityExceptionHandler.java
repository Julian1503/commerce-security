package com.julian.commerceauthsecurity.infrastructure.security;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.dto.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class SecurityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex) {
    return buildErrorResponse("Bad Credentials", HttpStatus.UNAUTHORIZED, "Invalid username or password");
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
    return buildErrorResponse("No permissions", HttpStatus.FORBIDDEN, "Access denied");
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    return buildErrorResponse("Method Argument Type Mismatch", HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
      return buildErrorResponse("Illegal Argument", HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<ErrorMessage> handleDatabaseException(Exception ex) {
        return buildErrorResponse("Fatal error", HttpStatus.INTERNAL_SERVER_ERROR, "Please try again later");
    }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    List<String> errorMessages = bindingResult.getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    return buildValidationResponse(errorMessages, HttpStatus.BAD_REQUEST, "Validation Error");
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    Throwable cause = ex.getMostSpecificCause();
    if (cause instanceof InvalidFormatException exception) {
      String fieldName = !exception.getPath().isEmpty()
              ? exception.getPath().get(0).getFieldName()
              : "unknown field";
      Object value = exception.getValue();
      return buildValidationResponse(
              List.of("Invalid value '" + value + "' for field '" + fieldName + "'"),
              HttpStatus.BAD_REQUEST,
              "Validation Error"
      );
    }
    return buildValidationResponse(
            List.of("Malformed JSON request"),
            HttpStatus.BAD_REQUEST,
            "Validation Error"
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
    return buildErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  private ResponseEntity<ErrorMessage> buildErrorResponse(String message, HttpStatus status, String details) {
    ErrorMessage errorResponse = new ErrorMessage(message, status.toString(), details);
    return new ResponseEntity<>(errorResponse, status);
  }

  private ResponseEntity<BaseResponse> buildValidationResponse(List<String> errors, HttpStatus status, String message) {
    BaseResponse response = new BaseResponse();
    response.setErrorResponse(errors);
    response.setStatus(status.value());
    response.setMessage(message);
    return new ResponseEntity<>(response, status);
  }

}
