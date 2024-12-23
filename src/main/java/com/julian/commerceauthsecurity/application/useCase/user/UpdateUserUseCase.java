package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.UpdateUserCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import com.julian.commerceshared.repository.UseCase;

import java.util.Optional;

public class UpdateUserUseCase implements UseCase<UpdateUserCommand, User> {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(UpdateUserCommand command) {
        Optional<User> user = userRepository.findById(command.id());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User userToUpdate = user.get();
        User userUpdated = userToUpdate.update(Avatar.create(command.avatar()), Username.create(command.username()), Email.create(command.email()));
        userRepository.save(userUpdated);
        return userUpdated;
    }
}
