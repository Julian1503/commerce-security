package com.julian.commerceauthsecurity.application.validation;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;

public class RoleValidation {
    public static void validate(RoleRepository roleRepository, Role role) {
        if(roleRepository.existsByName(role.getName().getValue())) {
            throw new IllegalArgumentException("Role already exists");
        }
    }
}
