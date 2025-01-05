package com.julian.commerceauthsecurity.application.query.permission;

import org.springframework.data.domain.Pageable;

public record FilteredPermissionQuery(String name, Pageable pagination) {
}
