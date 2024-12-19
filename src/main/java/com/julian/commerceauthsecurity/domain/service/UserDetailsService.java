package com.julian.commerceauthsecurity.domain.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
    UserDetails getUserDetails(String authToken);
}
