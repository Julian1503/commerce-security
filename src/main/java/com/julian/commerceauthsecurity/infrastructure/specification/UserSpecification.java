package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserEntity> Filters(String username, String email, String role, Boolean active,
                                                        LocalDate createdAfter, LocalDate createdBefore) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%"
                ));            }
            if (email != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"
                ));            }
            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            if (active != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), active));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
