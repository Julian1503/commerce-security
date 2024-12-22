package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.query.GetUserByIdQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceshared.repository.UseCase;


public class GetUserByIdUseCase implements UseCase<GetUserByIdQuery, User> {
    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User execute(GetUserByIdQuery command) {
        return userRepository.findById(command.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
