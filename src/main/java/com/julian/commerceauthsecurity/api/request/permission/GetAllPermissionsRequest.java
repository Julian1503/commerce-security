package com.julian.commerceauthsecurity.api.request.permission;

import com.julian.commerceauthsecurity.api.request.PagedRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class GetAllPermissionsRequest extends PagedRequest {
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores")
    private final String name;

    public GetAllPermissionsRequest(Integer page, Integer size, String sort, String direction, String name) {
        super(page, size, sort, direction);
        this.name = name;
    }
}
