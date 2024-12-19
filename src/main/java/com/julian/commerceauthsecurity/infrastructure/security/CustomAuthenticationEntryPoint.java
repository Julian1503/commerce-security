package com.julian.commerceauthsecurity.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.commerceshared.dto.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException)
      throws IOException, ServletException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ErrorMessage errorResponse = new ErrorMessage("Unauthorized","401", "Missing token");

    ObjectMapper mapper = new ObjectMapper();
    String responseJson = mapper.writeValueAsString(errorResponse);

    response.getWriter().write(responseJson);
  }
}