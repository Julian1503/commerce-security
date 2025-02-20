package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.UpdateUserCommand;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Avatar;
import com.julian.commerceauthsecurity.domain.valueobject.Email;
import com.julian.commerceauthsecurity.domain.valueobject.Username;
import com.julian.commerceshared.repository.UseCase;

public class UpdateUserUseCase implements UseCase<UpdateUserCommand, User> {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(UpdateUserCommand command) {
        User user = userRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User userUpdated = user.update(Avatar.create(command.avatar()), Username.create(command.username()), Email.create(command.email()));
        userRepository.save(userUpdated);
        return userUpdated;
    }
}
