package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.CreateUserCommand;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceshared.repository.UseCase;

import java.util.UUID;

public class CreateUserUseCase implements UseCase<CreateUserCommand, UUID> {
    private final UserRepository userRepository;
    private final PasswordEncryptionService passwordEncryptionService;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncryptionService passwordEncryptionService) {
        this.userRepository = userRepository;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @Override
    public UUID execute(CreateUserCommand command) {
        String passwordEncrypted = passwordEncryptionService.encrypt(command.getPassword());
        if(userRepository.existsByUsername(command.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if(Password.isValidFormat(command.getPassword())) {
            throw new IllegalArgumentException("Password is with an invalid format");
        }

        User modelUser = User.create(null, Avatar.getDefaultAvatar(), command.getUsername(), passwordEncrypted, command.getEmail(), Role.getDefaultRoles(), null);
        return userRepository.save(modelUser);
    }
}
