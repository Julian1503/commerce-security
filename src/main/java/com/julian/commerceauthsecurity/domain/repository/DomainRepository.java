package com.julian.commerceauthsecurity.domain.repository;

import java.util.Optional;

public interface DomainRepository<T, U> {
    U save(T entity);
    Optional<U> findById(U id);
    void deleteById(U id);
    boolean existsById(U id);
}
