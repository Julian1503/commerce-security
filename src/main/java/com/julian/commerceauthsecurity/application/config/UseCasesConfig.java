package com.julian.commerceauthsecurity.application.config;

import com.julian.commerceauthsecurity.application.command.account.LoginCommand;
import com.julian.commerceauthsecurity.application.command.user.ChangePasswordCommand;
import com.julian.commerceauthsecurity.application.command.user.CreateUserCommand;
import com.julian.commerceauthsecurity.application.query.GetUserByIdQuery;
import com.julian.commerceauthsecurity.application.query.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.application.useCase.account.LoginUseCase;
import com.julian.commerceauthsecurity.application.useCase.user.ChangeUserPasswordUseCase;
import com.julian.commerceauthsecurity.application.useCase.user.CreateUserUseCase;
import com.julian.commerceauthsecurity.application.useCase.user.GetUserByIdUseCase;
import com.julian.commerceauthsecurity.application.useCase.user.GetUsersWithFilterUseCase;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import com.julian.commerceauthsecurity.domain.service.TokenManager;
import com.julian.commerceshared.dto.AuthDto;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Collection;
import java.util.UUID;

@Configuration
public class UseCasesConfig {

    @Bean
    public UseCase<CreateUserCommand, UUID> createUserUseCase(UserRepository userRepository, PasswordEncryptionService passwordEncryptionService) {
        return new CreateUserUseCase(userRepository, passwordEncryptionService);
    }

    @Bean
    public UseCase<LoginCommand, AuthDto> loginUseCase(TokenManager tokenManager, AuthenticationManager authenticationManager) {
        return new LoginUseCase(tokenManager, authenticationManager);
    }

    @Bean
    public UseCase<ChangePasswordCommand, Boolean> changePasswordUseCase(UserRepository userRepository, PasswordEncryptionService passwordEncryptionService) {
        return new ChangeUserPasswordUseCase(userRepository, passwordEncryptionService);
    }

    @Bean
    public UseCase<GetUserByIdQuery, User> getUserByIdUseCase(UserRepository userRepository) {
        return new GetUserByIdUseCase(userRepository);
    }

    @Bean
    public UseCase<GetUsersWithFilterQuery, Collection<User>> getUsersWithFilterUseCase(UserRepository userRepository) {
        return new GetUsersWithFilterUseCase(userRepository);
    }
}
