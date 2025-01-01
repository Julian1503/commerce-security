package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleSpecificationTest {

    private Root<RoleEntity> root;
    private CriteriaQuery<?> query;
    private CriteriaBuilder criteriaBuilder;
    private Join<Object, Object> join;
    private Path<Object> path;
    private Predicate predicate;

    @BeforeEach
    void setUpMocks() {
        root = mock(Root.class);
        query = mock(CriteriaQuery.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        join = mock(Join.class);
        path = mock(Path.class);
        predicate = mock(Predicate.class);
    }

    @Test
    void testHasNameWithNull() {
        Specification<RoleEntity> spec = RoleSpecification.hasName(null);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNull(predicate, "Predicate should be null for null name");
    }

    @Test
    void testHasNameWithEmptyString() {
        Specification<RoleEntity> spec = RoleSpecification.hasName("");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNull(predicate, "Predicate should be null for empty name");
    }

    @Test
    void testHasNameWithValidName() {
        when(criteriaBuilder.equal(root.get("name"), "Admin")).thenReturn(predicate);
        Specification<RoleEntity> spec = RoleSpecification.hasName("Admin");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid name");
        verify(criteriaBuilder).equal(root.get("name"), "Admin");
    }


    @Test
    void testHasPermissionsWithNull() {
        Specification<RoleEntity> spec = RoleSpecification.hasPermissions(null);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNull(predicate, "Predicate should be null for null permissions");
    }

    @Test
    void testHasPermissionsWithEmptyList() {
        Specification<RoleEntity> spec = RoleSpecification.hasPermissions(List.of());
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNull(predicate, "Predicate should be null for empty permissions");
    }

    @Test
    void testHasPermissionsWithValidIds() {
        // Arrange
        Collection<UUID> permissions = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(root.join("permissions")).thenReturn(join);
        when(join.get("id")).thenReturn(path);
        when(path.in(permissions)).thenReturn(predicate);

        // Act
        Specification<RoleEntity> spec = RoleSpecification.hasPermissions(permissions);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);

        // Assert
        assertNotNull(predicate, "Predicate should not be null for valid permissions");
        verify(root).join("permissions");
        verify(join).get("id");
        verify(path).in(permissions);
    }

    @Test
    void testHasPermissionsWithDuplicateIds() {
        // Arrange
        UUID id = UUID.randomUUID();
        Collection<UUID> permissions = List.of(id, id);
        when(root.join("permissions")).thenReturn(join);
        when(join.get("id")).thenReturn(path);
        when(path.in(permissions)).thenReturn(predicate);

        // Act
        Specification<RoleEntity> spec = RoleSpecification.hasPermissions(permissions);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);

        // Assert
        assertNotNull(predicate, "Predicate should handle duplicates gracefully");
        verify(path).in(permissions);
    }
}