package com.julian.commerceauthsecurity.api.request.role;

import com.julian.commerceauthsecurity.api.request.PagedRequest;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class GetAllRolesRequest extends PagedRequest {
    private final String name;
    private final Collection<UUID> permissionIds;

    public GetAllRolesRequest(String name, Collection<UUID> permissionIds, Integer page, Integer size, String sort, String sortDirection) {
        super(page, size, sort, sortDirection);
        this.name = name;
        this.permissionIds = permissionIds;
    }
}
