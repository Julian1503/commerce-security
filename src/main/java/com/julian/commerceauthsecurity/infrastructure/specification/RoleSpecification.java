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
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<RoleEntity> hasId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<RoleEntity> hasPermissionId(UUID permissionId) {
        return (root, query, criteriaBuilder) -> {
            Join<Role, Permission> join = root.join("permissions");
            return criteriaBuilder.equal(join.get("id"), permissionId);
        };
    }

    public static Specification<RoleEntity> hasPermissionsName(Collection<String> permissionsName) {
        return (root, query, criteriaBuilder) -> {
            Join<Role, Permission> join = root.join("permissions");
            return join.get("name").in(permissionsName);
        };
    }
}
