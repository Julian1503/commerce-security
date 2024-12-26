package com.julian.commerceauthsecurity.infrastructure.security;

import com.julian.commerceauthsecurity.domain.service.SecurityContextInterface;
import com.julian.commerceauthsecurity.domain.service.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.julian.commerceauthsecurity.domain.service.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext implements SecurityContextInterface {
  private final Logger LOGGER = LoggerFactory.getLogger(SecurityContext.class);
  private final TokenManager tokenManager;
  private final UserDetailsService userDetailsService;

    public SecurityContext(TokenManager tokenManager, UserDetailsService userDetailsService) {
        this.tokenManager = tokenManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void addTokenToSecurityContext(final String token) {
    if (this.tokenManager.validateToken(token)) {
      this.setSecurityContext(token);
    } else {
      this.LOGGER.debug("Cannot set SecurityContext - the token supplied is invalid.");
    }
  }

  private void setSecurityContext(final String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(token);
    if (userDetails != null) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
      authentication.setDetails(userDetails);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      this.LOGGER.debug("Cannot set SecurityContext - cannot extract user details from the token supplied.");
    }

  }
}
