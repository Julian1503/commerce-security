package com.julian.commerceauthsecurity.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse {
    private final UUID id;
    private final String name;
    private final Collection<PermissionResponse> permissions;

    public RoleResponse(UUID id, String name, Collection<PermissionResponse> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }
}
