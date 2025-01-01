package com.julian.commerceauthsecurity.infrastructure.security;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.julian.commerceauthsecurity.domain.service.TokenManager;
import com.julian.commerceauthsecurity.domain.service.UserDetailsService;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SecurityContext.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SecurityContextTest {
    @Autowired
    private SecurityContext securityContext;

    @MockitoBean
    private TokenManager tokenManager;

    @MockitoBean
    private UserDetailsService userDetailsService;

    /**
     * Test {@link SecurityContext#addTokenToSecurityContext(String)}.
     * Method under test: {@link SecurityContext#addTokenToSecurityContext(String)}
     */
    @Test
    @DisplayName("Test addTokenToSecurityContext(String)")
    void testAddTokenToSecurityContext() throws UsernameNotFoundException {
        // Arrange
        when(tokenManager.validateToken(Mockito.<String>any())).thenReturn(true);
        when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));

        // Act
        securityContext.addTokenToSecurityContext("ABC123");

        // Assert
        verify(tokenManager).validateToken(eq("ABC123"));
        verify(userDetailsService).loadUserByUsername(eq("ABC123"));
    }

    /**
     * Test {@link SecurityContext#addTokenToSecurityContext(String)}.
     * Method under test: {@link SecurityContext#addTokenToSecurityContext(String)}
     */
    @Test
    @DisplayName("Test addTokenToSecurityContext(String)")
    void testAddTokenToSecurityContext2() throws UsernameNotFoundException {
        // Arrange
        when(tokenManager.validateToken(Mockito.<String>any())).thenReturn(true);
        when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(null);

        // Act
        securityContext.addTokenToSecurityContext("ABC123");

        // Assert
        verify(tokenManager).validateToken(eq("ABC123"));
        verify(userDetailsService).loadUserByUsername(eq("ABC123"));
    }

    /**
     * Test {@link SecurityContext#addTokenToSecurityContext(String)}.
     * Given {@link TokenManager} {@link TokenManager#validateToken(String)}
     * return {@code false}
     * Method under test: {@link SecurityContext#addTokenToSecurityContext(String)}
     */
    @Test
    @DisplayName("Test addTokenToSecurityContext(String); given TokenManager validateToken(String) return 'false'")
    void testAddTokenToSecurityContext_givenTokenManagerValidateTokenReturnFalse() {
        // Arrange
        when(tokenManager.validateToken(Mockito.<String>any())).thenReturn(false);

        // Act
        securityContext.addTokenToSecurityContext("ABC123");

        // Assert
        verify(tokenManager).validateToken(eq("ABC123"));
    }
}
