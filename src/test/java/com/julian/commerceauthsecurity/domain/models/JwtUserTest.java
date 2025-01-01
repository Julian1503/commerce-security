package com.julian.commerceauthsecurity.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUserTest {

    private UserDetails userDetails;
    private JwtUser jwtUser;

    @BeforeEach
    void setUp() {
        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();
        jwtUser = JwtUser.create(userDetails);
    }

    @Test
    void testCreateJwtUser() {
        assertNotNull(jwtUser);
        assertEquals(userDetails.getUsername(), jwtUser.getUsername());
        assertEquals(userDetails.getPassword(), jwtUser.getPassword());
        assertEquals(userDetails.getAuthorities(), jwtUser.getAuthorities());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(jwtUser.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(jwtUser.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(jwtUser.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(jwtUser.isEnabled());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }
}
