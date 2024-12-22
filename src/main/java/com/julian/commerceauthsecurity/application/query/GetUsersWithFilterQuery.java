package com.julian.commerceauthsecurity.application.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class GetUsersWithFilterQuery {
    private String username;
    private String email;
    private String role;
    private Boolean active;
    private LocalDate createdAfter;
    private LocalDate createdBefore;
    private Pageable pagination;
}
