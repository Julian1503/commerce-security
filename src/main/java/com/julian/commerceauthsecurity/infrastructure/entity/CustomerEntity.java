package com.julian.commerceauthsecurity.infrastructure.entity;

import com.nimbusds.openid.connect.sdk.claims.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table (name = "customer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_person_id_seq")
    @SequenceGenerator(name = "person_person_id_seq", sequenceName = "person_person_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true)
    private UUID id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(name = "finger_print_data")
    private Byte[] fingerPrintData;
    @Column(name = "photo")
    private String photo;
    @Column(name = "street", length = 50)
    private String street;
    @Column(name = "house_number", length = 10)
    private String houseNumber;
    @Column(name = "floor", length = 4)
    private String floor;
    @Column(name = "door", length = 2)
    private String door;
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}