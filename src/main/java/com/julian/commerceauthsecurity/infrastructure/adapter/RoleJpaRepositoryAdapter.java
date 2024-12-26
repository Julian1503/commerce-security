package com.julian.commerceauthsecurity.infrastructure.adapter;

import com.julian.commerceauthsecurity.domain.models.Role;
import com.julian.commerceauthsecurity.domain.repository.RoleRepository;
import com.julian.commerceauthsecurity.infrastructure.entity.RoleEntity;
import com.julian.commerceauthsecurity.infrastructure.repository.RolesJpaRepository;
import com.julian.commerceauthsecurity.infrastructure.specification.RoleSpecification;
import com.julian.commerceshared.repository.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleJpaRepositoryAdapter implements RoleRepository {

    private final RolesJpaRepository rolesJpaRepository;
    private final Mapper<Role, RoleEntity> roleMapper;

    public RoleJpaRepositoryAdapter(RolesJpaRepository rolesJpaRepository, Mapper<Role, RoleEntity> roleMapper) {
        this.rolesJpaRepository = rolesJpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public UUID save(Role roleEntity) {
        RoleEntity role = roleMapper.toSource(roleEntity);
        RoleEntity savedRole = rolesJpaRepository.save(role);
        return savedRole.getId();
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return rolesJpaRepository.findById(id).map(roleMapper::toTarget);
    }

    @Override
    public Page<Role> findFiltered(String name, Collection<UUID> roleIds, Pageable pageable) {
        Specification<RoleEntity> spec = RoleSpecification.hasName(name)
                .and(RoleSpecification.hasPermissions(roleIds));
        Page<RoleEntity> roleEntities = rolesJpaRepository.findAll(spec, pageable);
        return roleEntities.map(roleMapper::toTarget);
    }

    @Override
    public boolean existsByName(String name) {
        return rolesJpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        rolesJpaRepository.deleteById(id);
    }

    @Override
    public Collection<Role> findAllByIds(Collection<UUID> uuids) {
        Collection<RoleEntity> roleEntities = rolesJpaRepository.findAllById(uuids);
        return roleEntities.stream().map(roleMapper::toTarget).toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return rolesJpaRepository.existsById(id);
    }
}
