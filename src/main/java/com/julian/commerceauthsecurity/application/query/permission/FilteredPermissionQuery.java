package com.julian.commerceauthsecurity.application.query.permission;

import org.springframework.data.domain.Pageable;

import java.util.Collection;

public record FilteredPermissionQuery(String name, Pageable pagination) {
}
