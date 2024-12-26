package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceauthsecurity.domain.service.UserAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserAuthenticationManager {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }
}
