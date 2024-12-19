package com.julian.commerceauthsecurity.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue
    @Column(name="role_id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "role_name")
    private String name;

    @OneToMany
    @JoinTable(name="role_permission", joinColumns = @JoinColumn(name="role_id"), inverseJoinColumns = @JoinColumn(name="permission_id"))
    private List<PermissionEntity> permissions;

    public RoleEntity(String name) { this.name = name; }
}
