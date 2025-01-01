package com.julian.commerceauthsecurity.infrastructure.specification;

import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermissionSpecificationTest {

    private Root<PermissionEntity> root;
    private CriteriaQuery<?> query;
    private CriteriaBuilder criteriaBuilder;
    private Predicate predicate;

    @BeforeEach
    void setUpMocks() {
        root = mock(Root.class);
        query = mock(CriteriaQuery.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        predicate = mock(Predicate.class);
    }

    @Test
    void testHasNameWithNull() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        Specification<PermissionEntity> spec = PermissionSpecification.hasName(null);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should be conjunction for null name");
    }

    @Test
    void testHasNameWithEmptyString() {
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        when(criteriaBuilder.like(any(), anyString())).thenReturn(predicate);
        Specification<PermissionEntity> spec = PermissionSpecification.hasName("");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for empty name");
    }

    @Test
    void testHasNameWithValidName() {
        when(criteriaBuilder.like(any(), anyString())).thenReturn(predicate);
        Specification<PermissionEntity> spec = PermissionSpecification.hasName("Read");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should not be null for valid name");
        verify(criteriaBuilder).like(any(), eq("%read%"));
    }

    @Test
    void testHasNameCaseInsensitive() {
        when(criteriaBuilder.like(any(), anyString())).thenReturn(predicate);
        Specification<PermissionEntity> spec = PermissionSpecification.hasName("ReAd");
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate, "Predicate should match case-insensitively");
        verify(criteriaBuilder).like(any(), eq("%read%"));
    }
}