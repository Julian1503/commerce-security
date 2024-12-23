package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.DeleteUserCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceshared.repository.UseCase;

public class DeleteUserUseCase implements UseCase<DeleteUserCommand, User> {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(DeleteUserCommand command) {
        var user = userRepository.findById(command.id());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        var userToDelete = user.get();
        userRepository.delete(userToDelete.getUserId());
        return userToDelete;
    }
}
