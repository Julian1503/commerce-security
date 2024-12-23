package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecification {
    public static Specification<PermissionEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"
            );
        };
    }
}
