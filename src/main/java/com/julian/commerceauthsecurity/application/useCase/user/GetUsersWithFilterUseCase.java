package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.query.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceshared.repository.UseCase;

import java.util.Collection;

public class GetUsersWithFilterUseCase implements UseCase<GetUsersWithFilterQuery, Collection<User>> {
    private final UserRepository userRepository;

    public GetUsersWithFilterUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> execute(GetUsersWithFilterQuery query) {
        return userRepository.findAllWithFilter(
                query.getUsername(),
                query.getEmail(),
                query.getRole(),
                query.getActive(),
                query.getCreatedAfter(),
                query.getCreatedBefore(),
                query.getPagination());
    }
}
