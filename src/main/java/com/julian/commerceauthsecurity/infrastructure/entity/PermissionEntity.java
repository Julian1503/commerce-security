package com.julian.commerceauthsecurity.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "permissions")
public class PermissionEntity {
    @jakarta.persistence.Id
    @GeneratedValue
    @Column(name = "permission_id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "permission_name")
    private String name;

    public PermissionEntity(String name) { this.name = name; }
    public PermissionEntity() { }

}