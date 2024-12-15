package com.julian.commerceauthsecurity.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private UUID id;
    @Column(name = "avatar")
    private byte[] avatar;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;
    @ManyToMany
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<RoleEntity> roles;
    @ManyToOne
    private CustomerEntity user;

    public UserEntity(String username, List<RoleEntity> roles) {
        this.username = username;
        this.roles = roles;
    }
//
//
//    public void setPassword(String password) {
//        this.password = SpringCrypto.encrypt(password);
//    }
//    public List<String> getRolNames() {
//        if(!roles.isEmpty()) {
//            return roles.stream().map(RoleEntity::getName).toList();
//        }
//        return new ArrayList<>();
//    }
}