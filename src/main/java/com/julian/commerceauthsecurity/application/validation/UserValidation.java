package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.valueobject.Password;

public class UserValidation {
    public static void validate(UserRepository userRepository, User user) {
        if(userRepository.existsByUsername(user.getUsername().getValue())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if(userRepository.existsByEmail(user.getEmail().getValue())) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
