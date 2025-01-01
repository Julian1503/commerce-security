package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.query.user.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import com.julian.commerceshared.repository.UseCase;
import org.springframework.data.domain.Page;

public class GetAllUsersUseCase implements UseCase<GetUsersWithFilterQuery, Page<User>> {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> execute(GetUsersWithFilterQuery query) {
        return userRepository.findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                query.pagination());
    }
}
