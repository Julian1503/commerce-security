package com.julian.commerceauthsecurity.infrastructure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomAuthenticationEntryPoint.class})
@ExtendWith(SpringExtension.class)
class CustomAuthenticationEntryPointTest {
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private HttpServletResponseWrapper mockResponseWithWriter(PrintWriter writer) throws IOException {
        HttpServletResponseWrapper response = mock(HttpServletResponseWrapper.class);
        when(response.getWriter()).thenReturn(writer);
        doNothing().when(response).setContentType(Mockito.any());
        doNothing().when(response).setStatus(anyInt());
        return response;
    }

    @Test
    @DisplayName("Should return 401 and JSON content type when authentication fails")
    void testCommence_shouldReturn401WithJsonContentType() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponseWrapper response = mockResponseWithWriter(new PrintWriter(new StringWriter()));

        // Act
        customAuthenticationEntryPoint.commence(request, response, new AccountExpiredException("Account expired"));

        // Assert
        verify(response).setContentType(eq("application/json"));
        verify(response).setStatus(eq(401));
    }

    @Test
    @DisplayName("Should handle custom writer and return 401 status")
    void testCommence_shouldHandleCustomWriterAndReturn401() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponseWrapper response = mockResponseWithWriter(new PrintWriter(new StringWriter()));

        // Act
        customAuthenticationEntryPoint.commence(request, response, new AccountExpiredException("Account expired"));

        // Assert
        verify(response).setContentType(eq("application/json"));
        verify(response).setStatus(eq(401));
    }
}
