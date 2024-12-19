package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.ChangePasswordCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.PasswordEncryptionService;
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
        boolean isChangePasswordSuccessful = user.changePassword(command.getOldPassword(), command.getNewPassword(), passwordEncryptionService);
        if (isChangePasswordSuccessful) {
            userRepository.save(user);
        }
        return isChangePasswordSuccessful;
    }
}
