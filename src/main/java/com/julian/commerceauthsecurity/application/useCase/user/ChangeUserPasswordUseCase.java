package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.ChangePasswordCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
import com.julian.commerceauthsecurity.domain.valueobject.Password;
import com.julian.commerceshared.repository.UseCase;

import java.util.Optional;

public class ChangeUserPasswordUseCase implements UseCase<ChangePasswordCommand, Boolean> {
    private final UserRepository userRepository;
    private final PasswordEncryptionService passwordEncryptionService;

    public ChangeUserPasswordUseCase(UserRepository userRepository, PasswordEncryptionService passwordEncryptionService) {
        this.userRepository = userRepository;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @Override
    public Boolean execute(ChangePasswordCommand command) {
        Optional<User> userOptional = userRepository.findByUsername(command.username());
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        if (!passwordEncryptionService.matches(command.oldPassword(), user.getPassword().getValue())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if(Password.isValidFormat(command.newPassword())) {
            throw new IllegalArgumentException("New password is with an invalid format");
        }

        String encryptedNewPassword = passwordEncryptionService.encrypt(command.newPassword());
        user.changePassword(encryptedNewPassword);
        userRepository.save(user);
        return true;
    }
}
