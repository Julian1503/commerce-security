package com.julian.commerceauthsecurity.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity {
    @Id
    @GeneratedValue
    @Column(name = "permission_id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "permission_name")
    private String name;
}