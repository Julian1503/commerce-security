package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class RoleSpecification {
    public static Specification<RoleEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<RoleEntity> hasPermissions(Collection<UUID> permissionsId) {
        return (root, query, criteriaBuilder) -> {
            if (permissionsId == null || permissionsId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Role, Permission> join = root.join("permissions");
            return join.get("id").in(permissionsId);
        };
    }
}
