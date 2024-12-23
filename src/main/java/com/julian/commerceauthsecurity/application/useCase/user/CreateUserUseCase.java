package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.CreateBasicUserCommand;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import com.julian.commerceshared.repository.UseCase;

import java.util.UUID;

public class CreateUserUseCase implements UseCase<CreateBasicUserCommand, UUID> {
    private final UserRepository userRepository;
    private final PasswordEncryptionService passwordEncryptionService;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncryptionService passwordEncryptionService) {
        this.userRepository = userRepository;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @Override
    public UUID execute(CreateBasicUserCommand command) {
        String passwordEncrypted = passwordEncryptionService.encrypt(command.password());
        if(userRepository.existsByUsername(command.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if(Password.isValidFormat(command.password())) {
            throw new IllegalArgumentException("Password is with an invalid format");
        }

        User modelUser = User.create(null, Avatar.getDefaultAvatar(), Username.create(command.username()), Password.create(passwordEncrypted), Email.create(command.email()), Role.getDefaultRoles(), null);
        return userRepository.save(modelUser);
    }
}
