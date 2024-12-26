package com.julian.commerceauthsecurity.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse {
    private final UUID id;
    private final String name;
}
