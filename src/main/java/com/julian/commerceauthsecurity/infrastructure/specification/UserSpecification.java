package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;

public class UserSpecification {

    public static Specification<UserEntity> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<UserEntity> hasRoles(Collection<String> roleNames) {
        return (root, query, criteriaBuilder) ->root.join("roles").get("name").in(roleNames);
    }

    public static Specification<UserEntity> isActive(Boolean active) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), active);
    }

    public static Specification<UserEntity> createdAfter(LocalDate createdAfter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAfter);
    }

    public static Specification<UserEntity> createdBefore(LocalDate createdBefore) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdBefore);
    }
}
