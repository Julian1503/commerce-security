package com.julian.commerceauthsecurity.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.julian.commerceauthsecurity.domain.service.UserDetailsService;
import com.julian.commerceauthsecurity.infrastructure.implementation.LoadUserDetailService;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@ContextConfiguration(classes = {SecurityConfig.class, AuthenticationEntryPoint.class, JwtDecoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SecurityConfigTest {
    @MockitoBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConfig securityConfig;

    @MockitoBean
    private UserDetailsService userDetailsService;

    /**
     * Test
     * {@link SecurityConfig#authorizationManager(UserDetailsService, PasswordEncoder)}.
     * Method under test:
     * {@link SecurityConfig#authorizationManager(UserDetailsService, PasswordEncoder)}
     */
    @Test
    @DisplayName("Test authorizationManager(UserDetailsService, PasswordEncoder)")
    void testAuthorizationManager() {
        // Arrange
        LoadUserDetailService userDetailsService2 = new LoadUserDetailService(mock(UserJpaRepository.class));

        // Act
        AuthenticationManager actualAuthorizationManagerResult = securityConfig.authorizationManager(userDetailsService2,
                new PasswordEncoderConfig());

        // Assert
        assertTrue(actualAuthorizationManagerResult instanceof ProviderManager);
        List<AuthenticationProvider> providers = ((ProviderManager) actualAuthorizationManagerResult).getProviders();
        assertEquals(1, providers.size());
        AuthenticationProvider getResult = providers.get(0);
        assertTrue(getResult instanceof DaoAuthenticationProvider);
        assertTrue(((DaoAuthenticationProvider) getResult).getUserCache() instanceof NullUserCache);
        assertFalse(((DaoAuthenticationProvider) getResult).isForcePrincipalAsString());
        assertTrue(((ProviderManager) actualAuthorizationManagerResult).isEraseCredentialsAfterAuthentication());
        assertTrue(((DaoAuthenticationProvider) getResult).isHideUserNotFoundExceptions());
    }

    /**
     * Test {@link SecurityConfig#corsConfigurationSource()}.
     * Method under test: {@link SecurityConfig#corsConfigurationSource()}
     */
    @Test
    @DisplayName("Test corsConfigurationSource()")
    void testCorsConfigurationSource() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();

        // Act
        CorsConfigurationSource actualCorsConfigurationSourceResult = (new SecurityConfig(entryPoint,
                new NimbusJwtDecoder(new DefaultJWTProcessor<>()))).corsConfigurationSource();
        CorsConfiguration actualCorsConfiguration = actualCorsConfigurationSourceResult
                .getCorsConfiguration(new MockHttpServletRequest());

        // Assert
        assertTrue(actualCorsConfigurationSourceResult instanceof UrlBasedCorsConfigurationSource);
        Map<String, CorsConfiguration> corsConfigurations = ((UrlBasedCorsConfigurationSource) actualCorsConfigurationSourceResult)
                .getCorsConfigurations();
        assertEquals(1, corsConfigurations.size());
        CorsConfiguration getResult = corsConfigurations.get("/**");
        assertNull(getResult.getAllowPrivateNetwork());
        assertNull(getResult.getMaxAge());
        assertNull(getResult.getAllowedOriginPatterns());
        assertNull(getResult.getExposedHeaders());
        List<String> allowedHeaders = getResult.getAllowedHeaders();
        assertEquals(1, allowedHeaders.size());
        assertEquals(6, getResult.getAllowedMethods().size());
        assertTrue(getResult.getAllowCredentials());
        assertEquals(allowedHeaders, getResult.getAllowedOrigins());
        assertSame(getResult, actualCorsConfiguration);
    }
}
