package com.julian.commerceauthsecurity.api.controllers;

import com.julian.commerceauthsecurity.application.command.account.LoginCommand;
import com.julian.commerceshared.api.response.BaseResponse;
import com.julian.commerceshared.dto.AuthDto;
import com.julian.commerceshared.repository.UseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Mock
    private UseCase<LoginCommand, AuthDto> loginUseCase;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToken_Success() {
        // Arrange
        LoginCommand command = new LoginCommand("username", "password");
        AuthDto authDto = new AuthDto("token", "julian@hotmail.com");
        when(loginUseCase.execute(command)).thenReturn(authDto);

        // Act
        ResponseEntity<BaseResponse> response = loginController.token(command);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Authenticated successfully", response.getBody().getMessage());
        assertEquals(authDto, response.getBody().getResponse());
    }

    @Test
    void testToken_Failure() {
        // Arrange
        LoginCommand command = new LoginCommand("username", "password");
        when(loginUseCase.execute(command)).thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        ResponseEntity<BaseResponse> response = loginController.token(command);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Invalid credentials", response.getBody().getMessage());
    }
}
