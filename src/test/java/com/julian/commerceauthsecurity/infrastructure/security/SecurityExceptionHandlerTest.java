package com.julian.commerceauthsecurity.infrastructure.security;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.dto.ErrorMessage;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityExceptionHandlerTest {

    private SecurityExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SecurityExceptionHandler();
    }

    @Test
    @DisplayName("Test handleBadCredentialsException(BadCredentialsException)")
    void testHandleBadCredentialsException() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleBadCredentialsExceptionResult = securityExceptionHandler
                .handleBadCredentialsException(new BadCredentialsException("Msg"));

        // Assert
        HttpStatusCode statusCode = actualHandleBadCredentialsExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleBadCredentialsExceptionResult.getBody();
        assertEquals("401 UNAUTHORIZED", body.getCode());
        assertEquals("Bad Credentials", body.getError());
        assertEquals("Invalid username or password", body.getCause());
        assertEquals(401, actualHandleBadCredentialsExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.UNAUTHORIZED, statusCode);
        assertTrue(actualHandleBadCredentialsExceptionResult.hasBody());
        assertTrue(actualHandleBadCredentialsExceptionResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleAccessDeniedException(AccessDeniedException)")
    void testHandleAccessDeniedException() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleAccessDeniedExceptionResult = securityExceptionHandler
                .handleAccessDeniedException(new AccessDeniedException("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleAccessDeniedExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleAccessDeniedExceptionResult.getBody();
        assertEquals("403 FORBIDDEN", body.getCode());
        assertEquals("Access denied", body.getCause());
        assertEquals("No permissions", body.getError());
        assertEquals(403, actualHandleAccessDeniedExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.FORBIDDEN, statusCode);
        assertTrue(actualHandleAccessDeniedExceptionResult.hasBody());
        assertTrue(actualHandleAccessDeniedExceptionResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException); then StatusCode return HttpStatus")
    void testHandleMethodArgumentTypeMismatchException_thenStatusCodeReturnHttpStatus() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();
        Class<Object> requiredType = Object.class;

        // Act
        ResponseEntity<ErrorMessage> actualHandleMethodArgumentTypeMismatchExceptionResult = securityExceptionHandler
                .handleMethodArgumentTypeMismatchException(
                        new MethodArgumentTypeMismatchException("Value", requiredType, "0123456789ABCDEF", null, new Throwable()));

        // Assert
        HttpStatusCode statusCode = actualHandleMethodArgumentTypeMismatchExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleMethodArgumentTypeMismatchExceptionResult.getBody();
        assertEquals("400 BAD_REQUEST", body.getCode());
        assertEquals("Method Argument Type Mismatch", body.getError());
        assertEquals("Method parameter '0123456789ABCDEF': Failed to convert value of type 'java.lang.String' to required"
                + " type 'java.lang.Object'; null", body.getCause());
        assertEquals(400, actualHandleMethodArgumentTypeMismatchExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertTrue(actualHandleMethodArgumentTypeMismatchExceptionResult.hasBody());
        assertTrue(actualHandleMethodArgumentTypeMismatchExceptionResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleIllegalArgumentException(IllegalArgumentException); then StatusCode return HttpStatus")
    void testHandleIllegalArgumentException_thenStatusCodeReturnHttpStatus() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleIllegalArgumentExceptionResult = securityExceptionHandler
                .handleIllegalArgumentException(new IllegalArgumentException("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleIllegalArgumentExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleIllegalArgumentExceptionResult.getBody();
        assertEquals("400 BAD_REQUEST", body.getCode());
        assertEquals("Illegal Argument", body.getError());
        assertEquals("foo", body.getCause());
        assertEquals(400, actualHandleIllegalArgumentExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertTrue(actualHandleIllegalArgumentExceptionResult.hasBody());
        assertTrue(actualHandleIllegalArgumentExceptionResult.getHeaders().isEmpty());
    }


    @Test
    @DisplayName("Test handleDatabaseException(Exception)")
    void testHandleDatabaseException() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleDatabaseExceptionResult = securityExceptionHandler
                .handleDatabaseException(new Exception("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleDatabaseExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleDatabaseExceptionResult.getBody();
        assertEquals("500 INTERNAL_SERVER_ERROR", body.getCode());
        assertEquals("Fatal error", body.getError());
        assertEquals("Please try again later", body.getCause());
        assertEquals(500, actualHandleDatabaseExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualHandleDatabaseExceptionResult.hasBody());
        assertTrue(actualHandleDatabaseExceptionResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleValidationException(MethodArgumentNotValidException); then StatusCode return HttpStatus")
    void testHandleValidationException_thenStatusCodeReturnHttpStatus() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<BaseResponse> actualHandleValidationExceptionResult = securityExceptionHandler
                .handleValidationException(
                        new MethodArgumentNotValidException(null, new BindException("Target", "Object Name")));

        // Assert
        HttpStatusCode statusCode = actualHandleValidationExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        BaseResponse body = actualHandleValidationExceptionResult.getBody();
        assertEquals("Validation Error", body.getMessage());
        assertNull(body.getResponse());
        assertEquals(400, body.getStatus());
        assertEquals(400, actualHandleValidationExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertTrue(body.isSuccess());
        assertTrue(body.getErrorResponse().isEmpty());
        assertTrue(actualHandleValidationExceptionResult.hasBody());
        assertTrue(actualHandleValidationExceptionResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleHttpMessageNotReadable(HttpMessageNotReadableException); then StatusCode return HttpStatus")
    void testHandleHttpMessageNotReadable_thenStatusCodeReturnHttpStatus() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<BaseResponse> actualHandleHttpMessageNotReadableResult = securityExceptionHandler
                .handleHttpMessageNotReadable(new HttpMessageNotReadableException("https://example.org/example"));

        // Assert
        HttpStatusCode statusCode = actualHandleHttpMessageNotReadableResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        BaseResponse body = actualHandleHttpMessageNotReadableResult.getBody();
        List<String> errorResponse = body.getErrorResponse();
        assertEquals(1, errorResponse.size());
        assertEquals("Malformed JSON request", errorResponse.get(0));
        assertEquals("Validation Error", body.getMessage());
        assertNull(body.getResponse());
        assertEquals(400, body.getStatus());
        assertEquals(400, actualHandleHttpMessageNotReadableResult.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertTrue(body.isSuccess());
        assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
        assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleGeneralException(Exception); when Exception(String) with 'foo'; then StatusCode return HttpStatus")
    void testHandleGeneralException_whenExceptionWithFoo_thenStatusCodeReturnHttpStatus() {
        // Arrange
        SecurityExceptionHandler securityExceptionHandler = new SecurityExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleGeneralExceptionResult = securityExceptionHandler
                .handleGeneralException(new Exception("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleGeneralExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleGeneralExceptionResult.getBody();
        assertEquals("500 INTERNAL_SERVER_ERROR", body.getCode());
        assertEquals("Internal Server Error", body.getError());
        assertEquals("foo", body.getCause());
        assertEquals(500, actualHandleGeneralExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualHandleGeneralExceptionResult.hasBody());
        assertTrue(actualHandleGeneralExceptionResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test handleHttpMessageNotReadable with generic exception")
    void testHandleHttpMessageNotReadable_GenericException() {
        // Arrange
        SecurityExceptionHandler handler = new SecurityExceptionHandler();
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed JSON");

        // Act
        ResponseEntity<BaseResponse> response = handler.handleHttpMessageNotReadable(ex);

        // Assert
        BaseResponse body = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertNotNull(body);
        assertEquals("Validation Error", body.getMessage());
        assertEquals(1, body.getErrorResponse().size());
        assertEquals("Malformed JSON request", body.getErrorResponse().get(0));
    }

    @Test
    void testHandleHttpMessageNotReadable_UnknownField() {
        // Arrange
        InvalidFormatException exception = new InvalidFormatException(
                null,
                "Invalid value",
                "unknownValue",
                String.class
        );

        // No añade campo al path (queda vacío)
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid request", exception);

        // Act
        ResponseEntity<BaseResponse> response = handler.handleHttpMessageNotReadable(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        BaseResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Validation Error", body.getMessage());
        assertEquals(1, body.getErrorResponse().size());
        assertEquals("Invalid value 'unknownValue' for field 'unknown field'", body.getErrorResponse().get(0));
    }

    @Test
    void testHandleHttpMessageNotReadable_WithInvalidField() {
        // Arrange
        // Crear una excepción InvalidFormatException con un valor inválido
        InvalidFormatException exception = new InvalidFormatException(
                null,
                "Invalid value",
                "invalidValue",
                String.class
        );

        // Añadir un path con un nombre de campo simulado
        exception.prependPath(new JsonMappingException.Reference(null, "testField"));

        // Crear la excepción envuelta
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed JSON", exception);

        // Act
        ResponseEntity<BaseResponse> response = handler.handleHttpMessageNotReadable(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        BaseResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Validation Error", body.getMessage());
        assertEquals(1, body.getErrorResponse().size());
        assertEquals("Invalid value 'invalidValue' for field 'testField'", body.getErrorResponse().get(0));
    }
}
