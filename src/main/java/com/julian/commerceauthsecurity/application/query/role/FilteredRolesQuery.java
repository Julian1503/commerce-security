package com.julian.commerceauthsecurity.application.query.role;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.UUID;

public record FilteredRolesQuery (String name, Collection<UUID> rolesId, Pageable pagination) {
}
