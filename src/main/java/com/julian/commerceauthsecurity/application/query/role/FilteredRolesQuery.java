package com.julian.commerceauthsecurity.application.query.role;

import org.springframework.data.domain.Pageable;

import java.util.Collection;

public record FilteredRolesQuery (String name, Collection<String> roleNames, Pageable pagination) {
}
