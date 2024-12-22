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
        Optional<User> userOptional = userRepository.findByUsername(command.getUsername());
        if (!userOptional.isPresent()) {
            return false;
        }

        User user = userOptional.get();
        if (!passwordEncryptionService.matches(command.getOldPassword(), user.getPassword().getValue())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if(Password.isValidFormat(command.getNewPassword())) {
            throw new IllegalArgumentException("New password is with an invalid format");
        }

        String encryptedNewPassword = passwordEncryptionService.encrypt(command.getNewPassword());
        user.changePassword(encryptedNewPassword);
        userRepository.save(user);
        return true;
    }
}
