package com.julian.commerceauthsecurity.application.useCase.account;

import com.julian.commerceauthsecurity.application.command.account.LoginCommand;
import com.julian.commerceauthsecurity.domain.service.TokenManager;
import com.julian.commerceshared.dto.AuthDto;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


public class LoginUseCase implements UseCase<LoginCommand, AuthDto> {
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    public LoginUseCase(TokenManager tokenManager, AuthenticationManager authenticationManager) {
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthDto execute(LoginCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(command.username(), command.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String tokenGenerated = tokenManager.generateToken(authentication);
        return new AuthDto(tokenGenerated, command.username());
    }
}
