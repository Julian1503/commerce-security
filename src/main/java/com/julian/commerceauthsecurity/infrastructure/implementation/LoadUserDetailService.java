package com.julian.commerceauthsecurity.infrastructure.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.julian.commerceauthsecurity.domain.models.JwtUser;
import com.julian.commerceauthsecurity.domain.service.UserDetailsService;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.UserJpaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collection;

import static java.util.Arrays.stream;

@Service
public class LoadUserDetailService implements UserDetailsService {
    private final Logger LOGGER = LogManager.getLogger(LoadUserDetailService.class);
    private final UserJpaRepository userRepository;

    public LoadUserDetailService(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    Collection<GrantedAuthority> roleNames = user.getRoles().stream()
                            .map(RoleEntity::getName)
                            .map(roleName -> (GrantedAuthority) () -> roleName)
                            .toList();

                    return JwtUser.create(new User(user.getUsername(), user.getPassword(), roleNames));
                }).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails getUserDetails(String authToken) {
        JwtUser jwtUser = null;

        try {
            DecodedJWT jwt = JWT.decode(authToken);
            String username = jwt.getClaim("username").asString();
            String[] scopes = new String[0];
            if (!jwt.getClaim("scope").isNull()) {
                scopes = (String[])jwt.getClaim("scope").asArray(String.class);
            }
            jwtUser = JwtUser.create(new User(username, "", stream(scopes).map(s -> (GrantedAuthority) () -> s).toList()));
        } catch (Exception var9) {
            LOGGER.error("Error getting user details", var9);
        }

        return jwtUser;
    }
}
