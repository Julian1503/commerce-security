package com.julian.commerceauthsecurity.application.useCase.user;

import com.julian.commerceauthsecurity.application.query.user.GetUsersWithFilterQuery;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import util.UserBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUsersUseCase getAllUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldReturnUsersPage_WhenCalledWithValidQuery() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        User user = UserBuilder.getValidUser();
        Page<User> expectedPage = new PageImpl<>(Collections.singletonList(user));
        GetUsersWithFilterQuery query = new GetUsersWithFilterQuery("TestUsername", "TestEmail", null, null, null, null, pageable);

        when(userRepository.findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        )).thenReturn(expectedPage);

        // Act
        Page<User> result = getAllUserUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(user.getUsername().getValue(), result.getContent().get(0).getUsername().getValue());
        verify(userRepository, times(1)).findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        );
    }

    @Test
    void execute_ShouldReturnEmptyPage_WhenNoUsersFound() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> emptyPage = Page.empty();
        GetUsersWithFilterQuery query = new GetUsersWithFilterQuery("InvalidUsername", "InvalidEmail", null, null, null, null, pageable);
        when(userRepository.findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        )).thenReturn(emptyPage);

        // Act
        Page<User> result = getAllUserUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        );
    }

    @Test
    void execute_ShouldHandleNullPermissions() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> emptyPage = Page.empty();
        GetUsersWithFilterQuery query = new GetUsersWithFilterQuery("TestUsername", "TestEmail", null, null, null, null, pageable);
        when(userRepository.findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        )).thenReturn(emptyPage);

        // Act
        Page<User> result = getAllUserUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        );
    }

    @Test
    void execute_ShouldHandleEmptyName() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> emptyPage = Page.empty();
        GetUsersWithFilterQuery query = new GetUsersWithFilterQuery("", "TestEmail", null, null, null, null, pageable);
        when(userRepository.findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        )).thenReturn(emptyPage);

        // Act
        Page<User> result = getAllUserUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAllWithFilter(
                query.username(),
                query.email(),
                query.role(),
                query.active(),
                query.createdAfter(),
                query.createdBefore(),
                pageable
        );
    }
}
