package com.julian.commerceauthsecurity.infrastructure.implementation;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.julian.commerceauthsecurity.configuration.RsaKeys;
import com.julian.commerceauthsecurity.domain.models.User;
import com.julian.commerceauthsecurity.domain.service.RSAKeyProvider;
import com.julian.commerceauthsecurity.domain.service.UserAuthenticationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import util.UserBuilder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtTokenServiceTest {

    @InjectMocks
    private JwtTokenService jwtTokenService;

    @Mock
    private UserAuthenticationManager userAuthenticationManager;

    @Mock
    private JwtEncoder encoder;

    @Mock
    private RSAKeyProvider rsaKeys;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        rsaKeys = new RsaKeys(publicKey, privateKey);

        jwtTokenService = new JwtTokenService(userAuthenticationManager, encoder, rsaKeys);
    }

    @Test
    void testGenerateToken_ValidAuthentication_ReturnsToken() {
        // Arrange
        User user = UserBuilder.getValidUser();
        when(authentication.getName()).thenReturn(user.getUsername().getValue());
        when(userAuthenticationManager.getUserFromAuthentication(authentication)).thenReturn(Optional.of(user));


        Jwt mockParameters = mock(Jwt.class);
        when(mockParameters.getTokenValue()).thenReturn("mockToken");
        when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockParameters);

        // Act
        String token = jwtTokenService.generateToken(authentication);

        // Assert
        assertNotNull(token);
        assertEquals("mockToken", token);
    }

    @Test
    void testVerifyToken_InvalidToken_ThrowsException() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        DecodedJWT result = jwtTokenService.verifyToken(invalidToken);

        // Assert
        assertNull(result, "Invalid token should return null.");
    }

    @Test
    void testGetClaimsFromToken_InvalidToken_LogsError() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        boolean result = jwtTokenService.validateToken(invalidToken);

        // Assert
        assertFalse(result, "Invalid token should return false.");
    }

    @Test
    void testLoggerErrorOnVerifyToken() {
        Logger logger = (Logger) LoggerFactory.getLogger(JwtTokenService.class);

        @SuppressWarnings("unchecked")
        Appender<ILoggingEvent> mockAppender = mock(Appender.class);

        logger.addAppender(mockAppender);

        ArgumentCaptor<ILoggingEvent> captor = ArgumentCaptor.forClass(ILoggingEvent.class);

        jwtTokenService.verifyToken("invalid.token.here");

        verify(mockAppender, atLeastOnce()).doAppend(captor.capture());

        ILoggingEvent logEvent = captor.getValue();
        assertEquals(Level.ERROR, logEvent.getLevel(), "El nivel del log debería ser ERROR.");
        assertTrue(logEvent.getFormattedMessage().contains("Error while verifying authentication token."),
                "El mensaje del log debería contener 'Error while verifying authentication token.'");
    }

    @Test
    void testValidateToken_ValidToken_ReturnsTrue() {
        // Arrange
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqdWxpYW4xMjMiLCJzY29wZSI6WyJVU0VSIl0sImlzcyI6InNlbGYiLCJleHAiOjE3MzU0NjIyNTIsImlhdCI6MTczNTM3NTg1Miwic2NpZCI6ImRjOTczYzRjLTgwYWYtNDQ5Zi05MGVhLWU4ZDg3NGUxNzUxNSIsImVtYWlsIjoianVsaWFuQGV4YW1wbGUuY29tIiwidXNlcm5hbWUiOiJqdWxpYW4xMjMifQ.K-fKDW53azK4kVFshE9b2astMypv8jFxkrfHNn96OJo4_AEVnmO7b9IU_LRQmGbGdGCnKA-yadX1GTPaLLYlLHEvuXr6YfXDbvfxkTAOZ8OSPRceHvBqjfaduhwYm9sGRV0MkUt2_XnbkkEG9ZvXQz8fcV_of_BTbv2j6r1z_hVv51Lm5meNjqalXvMRXlHdhQWp5umTCcedjpk2eSDO-jFvzGmVuGxY57FvbKRW0oREDis62vFq2QHQBz4cKgdZoi8dkwiHG_5NJnGIbWIOcxiJoJt6ohhuSc7xI2w3HS9L6ph88U0My-ZyOVB-MViRbL1Gh3erdCMOL1SBw-QGZQ";
        DecodedJWT jwt = mock(DecodedJWT.class);
        JwtTokenService spyJwtTokenService = spy(jwtTokenService);
        doReturn(jwt).when(spyJwtTokenService).decodeToken(token);

        // Act
        boolean isValid = spyJwtTokenService.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken_ReturnsFalse() {
        // Arrange
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqdWxpYW4xMjMiLCJzY29wZSI6WyJVU0VSIl0sImlzcyI6InNlbGYiLCJleHAiOjE3MzU0NjIyNTIsImlhdCI6MTczNTM3NTg1Miwic2NpZCI6ImRjOTczYzRjLTgwYWYtNDQ5Zi05MGVhLWU4ZDg3NGUxNzUxNSIsImVtYWlsIjoianVsaWFuQGV4YW1wbGUuY29tIiwidXNlcm5hbWUiOiJqdWxpYW4xMjMifQ.K-fKDW53azK4kVFshE9b2astMypv8jFxkrfHNn96OJo4_AEVnmO7b9IU_LRQmGbGdGCnKA-yadX1GTPaLLYlLHEvuXr6YfXDbvfxkTAOZ8OSPRceHvBqjfaduhwYm9sGRV0MkUt2_XnbkkEG9ZvXQz8fcV_of_BTbv2j6r1z_hVv51Lm5meNjqalXvMRXlHdhQWp5umTCcedjpk2eSDO-jFvzGmVuGxY57FvbKRW0oREDis62vFq2QHQBz4cKgdZoi8dkwiHG_5NJnGIbWIOcxiJoJt6ohhuSc7xI2w3HS9L6ph88U0My-ZyOVB-MViRbL1Gh3erdCMOL1SBw-QGZQ";
        JwtTokenService spyJwtTokenService = spy(jwtTokenService);
        when(spyJwtTokenService.decodeToken(token)).thenReturn(null);
        // Act
        boolean isValid = spyJwtTokenService.validateToken(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testVerifyToken_ValidToken_ReturnsDecodedJWT() {
        // Arrange
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqdWxpYW4xMjMiLCJzY29wZSI6WyJVU0VSIl0sImlzcyI6InNlbGYiLCJleHAiOjE3MzU0NjIyNTIsImlhdCI6MTczNTM3NTg1Miwic2NpZCI6ImRjOTczYzRjLTgwYWYtNDQ5Zi05MGVhLWU4ZDg3NGUxNzUxNSIsImVtYWlsIjoianVsaWFuQGV4YW1wbGUuY29tIiwidXNlcm5hbWUiOiJqdWxpYW4xMjMifQ.K-fKDW53azK4kVFshE9b2astMypv8jFxkrfHNn96OJo4_AEVnmO7b9IU_LRQmGbGdGCnKA-yadX1GTPaLLYlLHEvuXr6YfXDbvfxkTAOZ8OSPRceHvBqjfaduhwYm9sGRV0MkUt2_XnbkkEG9ZvXQz8fcV_of_BTbv2j6r1z_hVv51Lm5meNjqalXvMRXlHdhQWp5umTCcedjpk2eSDO-jFvzGmVuGxY57FvbKRW0oREDis62vFq2QHQBz4cKgdZoi8dkwiHG_5NJnGIbWIOcxiJoJt6ohhuSc7xI2w3HS9L6ph88U0My-ZyOVB-MViRbL1Gh3erdCMOL1SBw-QGZQ";
        DecodedJWT jwt = mock(DecodedJWT.class);
        JwtTokenService spyJwtTokenService = spy(jwtTokenService);
        doReturn(jwt).when(spyJwtTokenService).verifyToken(token);

        // Act
        DecodedJWT result = spyJwtTokenService.verifyToken(token);

        // Assert
        assertNotNull(result);
    }


    @Test
    void testDecodeToken_ValidToken_ReturnsDecodedJWT() {
        // Arrange
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqdWxpYW4xMjMiLCJzY29wZSI6WyJVU0VSIl0sImlzcyI6InNlbGYiLCJleHAiOjE3MzUxODQ0OTYsImlhdCI6MTczNTA5ODA5Niwic2NpZCI6ImRjOTczYzRjLTgwYWYtNDQ5Zi05MGVhLWU4ZDg3NGUxNzUxNSIsImVtYWlsIjp7InZhbHVlIjoianVsaWFuQGV4YW1wbGUuY29tIn19.QDHfMXFFF9oN146k7RhB__6B2DvddMfkcNcId5oYFrMdaev_zc234j7vCHnjc_eFr6kLUAQUwjQ-R5pOvRW4hk5_vLqzJjXzkH1SBWtCiQLekG7ibgHolkRrNqH8ynXRHDkkkOvzAAnj9wvEyfMM8ovie2a26I8PqldJBE65fTUEekAl6ZB61jQBA2-JC3JlpqwvTh_GPuaNLh6aNraaE1dWhcIh5Bxine3k5y-0IO2FjrhADjshePb7PuFo8Wo9zckg8nYyIZnz3U8AW2zzKURJ_xo_LuR0A5FyWCB6g9HXi2e15k_LpBkw4anoXq0MYS67t628CT_pi6HXsFi8Xw";
        DecodedJWT jwt = mock(DecodedJWT.class);
        JwtTokenService spyJwtTokenService = spy(jwtTokenService);
        doReturn(jwt).when(spyJwtTokenService).decodeToken(token);

        // Act
        DecodedJWT result = jwtTokenService.decodeToken(token);

        // Assert
        assertNotNull(result);
    }
}
