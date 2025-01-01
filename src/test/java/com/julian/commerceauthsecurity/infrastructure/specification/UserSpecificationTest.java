package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.infrastructure.entity.UserEntity;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSpecificationTest {

    private Root<UserEntity> root;
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
    void testHasUsernameWithNull() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.hasUsername(null);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should be conjunction for null username");
    }

    @Test
    void testHasUsernameWithEmptyString() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.hasUsername("");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should be conjunction for empty username");
    }

    @Test
    void testHasUsernameWithValidUsername() {
        when(criteriaBuilder.equal(root.get("username"), "johndoe")).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.hasUsername("johndoe");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid username");
        verify(criteriaBuilder).equal(root.get("username"), "johndoe");
    }

    @Test
    void testHasEmailWithNull() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.hasEmail(null);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should be conjunction for null email");
    }

    @Test
    void testHasEmailWithEmptyString() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.hasEmail("");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should be conjunction for empty email");
    }

    @Test
    void testHasEmailWithValidEmail() {
        when(criteriaBuilder.equal(root.get("email"), "johndoe@example.com")).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.hasEmail("johndoe@example.com");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid email");
        verify(criteriaBuilder).equal(root.get("email"), "johndoe@example.com");
    }

    @Test
    void testHasRolesWithValidRoleNames() {
        Collection<String> roleNames = List.of("ADMIN", "USER");
        when(join.get("id")).thenReturn(path);
        when(path.in(roleNames)).thenReturn(predicate);
        when(root.join("roles")).thenReturn(join);
        when(join.get(anyString())).thenReturn(path);
        Specification<UserEntity> spec = UserSpecification.hasRoles(roleNames);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid roles");
    }

    @Test
    void testHasRolesWithValidRoleNames_NullList() {
        Collection<String> roleNames = null;
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        when(root.join("roles")).thenReturn(join);
        Specification<UserEntity> spec = UserSpecification.hasRoles(roleNames);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid roles");
    }

    @Test
    void testHasRolesWithValidRoleNames_EmptyList() {
        Collection<String> roleNames = Collections.emptyList();
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        when(root.join("roles")).thenReturn(join);
        Specification<UserEntity> spec = UserSpecification.hasRoles(roleNames);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid roles");
    }


    @Test
    void testIsActiveWithTrue() {
        when(criteriaBuilder.equal(root.get("active"), true)).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.isActive(true);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for active status");
    }

    @Test
    void testIsActiveWithNull() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.isActive(null);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for active status");
    }

    @Test
    void testCreatedAfterWithValidDate() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        when(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), date)).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.createdAfter(date);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for createdAfter date");
    }

    @Test
    void testCreatedAfterWithNullDate() {
        LocalDate date = null;
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.createdAfter(date);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for createdAfter date");
    }

    @Test
    void testCreatedBeforeWithValidDate() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        when(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), date)).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.createdBefore(date);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for createdBefore date");
    }

    @Test
    void testCreatedBeforeWithNullDate() {
        LocalDate date = null;
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<UserEntity> spec = UserSpecification.createdBefore(date);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for createdBefore date");
    }
}