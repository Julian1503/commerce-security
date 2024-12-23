package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.command.user.AssignRoleToUserCommand;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceshared.repository.UseCase;

import java.util.Collection;

public class AssignRoleToUserUseCase implements UseCase<AssignRoleToUserCommand, Boolean> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AssignRoleToUserUseCase(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Boolean execute(AssignRoleToUserCommand command) {
        if(command.roleIds().isEmpty() || command.userId() == null) {
            throw new IllegalArgumentException("User or Role cannot be null");
        }

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        Collection<Role> existingRoles = roleRepository.findAllByIds(command.roleIds());

        if (existingRoles.size() != command.roleIds().size()) {
            throw new IllegalArgumentException("One or more permissions do not exist.");
        }

        User userUpdated = user.assignRoles(existingRoles);

        userRepository.save(userUpdated);
        return true;
    }
}
