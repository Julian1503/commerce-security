package com.julian.commerceauthsecurity.infrastructure.security;

import com.julian.commerceauthsecurity.domain.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final AuthenticationEntryPoint entryPoint;
  private final JwtDecoder jwtDecoder;

  public SecurityConfig(AuthenticationEntryPoint entryPoint, JwtDecoder jwtDecoder) {
      this.entryPoint = entryPoint;
      this.jwtDecoder = jwtDecoder;
  }

  @Bean
  public AuthenticationManager authorizationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE","PUT","PATCH"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(this::getAuthorizationManagerRequestMatcherRegistry)
            .headers(this::configureHeaders)
            .oauth2ResourceServer(this::configureOAuth2ResourceServer)
            .sessionManagement(this::configureSessionManagement)
            .exceptionHandling(this::configureExceptionHandling)
            .userDetailsService(userDetailsService)
            .build();
  }

  private void getAuthorizationManagerRequestMatcherRegistry(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
    auth
            .requestMatchers("/api/login", "/api/user/create-basic-user").permitAll()
            .requestMatchers("/api/**").authenticated()
            .anyRequest()
            .denyAll();
  }

  private void configureHeaders(HeadersConfigurer<HttpSecurity> headers) {
    headers.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
  }

  private void configureOAuth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2) {
    oauth2.jwt(jwt -> jwt.decoder(jwtDecoder));
  }

  private void configureSessionManagement(SessionManagementConfigurer<HttpSecurity> session) {
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> ex) {
    ex.authenticationEntryPoint(entryPoint);
  }
}
