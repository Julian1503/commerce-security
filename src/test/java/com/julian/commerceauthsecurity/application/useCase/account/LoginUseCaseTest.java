package com.julian.commerceauthsecurity.application.useCase.account;

import com.julian.commerceauthsecurity.application.command.account.LoginCommand;
import com.julian.commerceauthsecurity.domain.service.TokenManager;
import com.julian.commerceshared.dto.AuthDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginUseCaseTest {
    private LoginUseCase loginUseCase;
    @Mock
    private TokenManager tokenManager;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new LoginUseCase(tokenManager, authenticationManager);
    }

    @Test
    public void testExecuteMethod() {
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(tokenManager.generateToken(any())).thenReturn("mockToken");
        LoginCommand command = new LoginCommand("mockUsername", "mockPassword");
        var result = loginUseCase.execute(command);
        assertEquals(command.username(), result.getEmail());
        assertEquals("mockToken", result.getToken());
    }
}
