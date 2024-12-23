package com.julian.commerceauthsecurity.application.query.user;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;

public record GetUsersWithFilterQuery (String username, String email, Collection<String> role, Boolean active, LocalDate createdAfter, LocalDate createdBefore, Pageable pagination) {

}
