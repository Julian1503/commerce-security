package com.julian.commerceauthsecurity.api.request.role;

import com.julian.commerceauthsecurity.api.request.PagedRequest;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class GetAllRolesRequest extends PagedRequest {
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores")
    private final String name;
    private final Collection<UUID> permissionIds;

    public GetAllRolesRequest(String name, Collection<UUID> permissionIds, Integer page, Integer size, String sort, String sortDirection) {
        super(page, size, sort, sortDirection);
        this.name = name;
        this.permissionIds = permissionIds;
    }
}
