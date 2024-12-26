package com.julian.commerceauthsecurity.domain.repository;

import com.julian.commerceauthsecurity.domain.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    UUID save(Role roleEntity);
    Optional<Role> findById(UUID id);
    Page<Role> findFiltered(String name, Collection<UUID> roleIds, Pageable pageable);
    boolean existsByName(String name);
    void deleteById(UUID id);
    Collection<Role> findAllByIds(Collection<UUID> uuids);
    boolean existsById(UUID id);
}
