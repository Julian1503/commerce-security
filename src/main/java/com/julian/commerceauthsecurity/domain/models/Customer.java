package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.Gender;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Customer extends AbstractAggregateRoot<Customer> {
    private final UUID id;
    private final String name;
    private final String lastName;
    private final String phoneNumber;
    private final String fingerPrintData;
    private final String photo;
    private final String street;
    private final String houseNumber;
    private final String floor;
    private final String door;
    private final Gender gender;
    private final Date birthDate;
    private final UUID userId;

    private Customer(
            UUID id,
            String name,
            String lastName,
            String phoneNumber,
            String fingerPrintData,
            String photo,
            String street,
            String houseNumber,
            String floor,
            String door,
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
        this.street = street;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.door = door;
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
            String street,
            String houseNumber,
            String floor,
            String door,
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
                name,
                lastName,
                phoneNumber,
                fingerPrintData,
                photo,
                street,
                houseNumber,
                floor,
                door,
                gender,
                birthDate,
                userId
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFingerPrintData() {
        return fingerPrintData;
    }

    public String getPhoto() {
        return photo;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getFloor() {
        return floor;
    }

    public String getDoor() {
        return door;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void customerRegistration() {

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