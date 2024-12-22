package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.Address;
import com.julian.commerceauthsecurity.domain.valueobject.Gender;
import com.julian.commerceauthsecurity.domain.valueobject.Name;
import com.julian.commerceauthsecurity.domain.valueobject.PhoneNumber;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Customer extends AbstractAggregateRoot<Customer> {
    private final UUID id;
    private final Name name;
    private final Name lastName;
    private final PhoneNumber phoneNumber;
    private final String fingerPrintData;
    private final String photo;
    private final Address address;
    private final Gender gender;
    private final Date birthDate;
    private final UUID userId;

    private Customer(
            UUID id,
            Name name,
            Name lastName,
            PhoneNumber phoneNumber,
            String fingerPrintData,
            String photo,
            Address address,
            Gender gender,
            Date birthDate,
            UUID userId
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.fingerPrintData = fingerPrintData;
        this.photo = photo;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
        this.userId = userId;
    }

    public static Customer create(
            UUID id,
            String name,
            String lastName,
            String phoneNumber,
            String fingerPrintData,
            String photo,
            Address address,
            Gender gender,
            Date birthDate,
            UUID userId
    ) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        return new Customer(
                id == null ? UUID.randomUUID() : id,
                Name.create(name),
                Name.create(lastName),
                PhoneNumber.create(phoneNumber),
                fingerPrintData,
                photo,
                address,
                gender,
                birthDate,
                userId
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", photo='" + photo + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", userId=" + userId +
                '}';
    }
}