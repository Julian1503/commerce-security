package com.julian.commerceauthsecurity.api.request.permission;

import com.julian.commerceauthsecurity.api.request.PagedRequest;
import lombok.Getter;


@Getter
public class GetAllPermissionsRequest extends PagedRequest {
    private final String name;

    public GetAllPermissionsRequest(int page, int size, String sort, String direction, String name) {
        super(page, size, sort, direction);
        this.name = name;
    }
}
