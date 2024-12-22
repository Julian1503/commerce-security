package com.julian.commerceauthsecurity.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String username;
    private final String email;
    private final String avatar;
    private final UUID id;

    public UserResponse(String username, String email, String avatar, UUID id) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.id = id;
    }
}
