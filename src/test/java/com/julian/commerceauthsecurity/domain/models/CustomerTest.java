package com.julian.commerceauthsecurity.domain.models;

import com.julian.commerceauthsecurity.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private UUID customerId;
    private Name firstName;
    private Name lastName;
    private PhoneNumber phoneNumber;
    private String fingerPrintData;
    private String photo;
    private Address address;
    private Gender gender;
    private Date birthDate;
    private UUID userId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        firstName = Name.create("John");
        lastName = Name.create("Doe");
        phoneNumber = PhoneNumber.create("1234567890");
        fingerPrintData = "fingerprint_data";
        photo = "photo_url";
        address = Address.create("123 Main St", "City", "State", "12345");
        gender = Gender.MALE;
        birthDate = new Date();
        userId = UUID.randomUUID();
    }

    @Test
    void testCreateCustomerWithValidData() {
        Customer customer = Customer.create(
                customerId,
                "John",
                "Doe",
                "1234567890",
                fingerPrintData,
                photo,
                address,
                gender,
                birthDate,
                userId
        );

        assertNotNull(customer);
        assertEquals(customerId, customer.getId());
        assertEquals(firstName, customer.getName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(phoneNumber, customer.getPhoneNumber());
        assertEquals(fingerPrintData, customer.getFingerPrintData());
        assertEquals(photo, customer.getPhoto());
        assertEquals(address, customer.getAddress());
        assertEquals(gender, customer.getGender());
        assertEquals(birthDate, customer.getBirthDate());
        assertEquals(userId, customer.getUserId());
    }

    @Test
    void testCreateCustomerWithInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> Customer.create(
                null, "", "Doe", "1234567890", fingerPrintData, photo, address, gender, birthDate, userId));

        assertThrows(IllegalArgumentException.class, () -> Customer.create(
                customerId, "John", "", "1234567890", fingerPrintData, photo, address, gender, birthDate, userId));

        assertThrows(IllegalArgumentException.class, () -> Customer.create(
                customerId, "John", "Doe", "", fingerPrintData, photo, address, null, birthDate, userId));

        assertThrows(IllegalArgumentException.class, () -> Customer.create(
                customerId, "John", "Doe", "1234567890", fingerPrintData, photo, address, gender, null, userId));
    }

    @Test
    void testEqualityAndHashCode() {
        Customer customer1 = Customer.create(
                customerId, "John", "Doe", "1234567890", fingerPrintData, photo, address, gender, birthDate, userId);
        Customer customer2 = Customer.create(
                customerId, "John", "Doe", "1234567890", fingerPrintData, photo, address, gender, birthDate, userId);

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());

        Customer customer3 = Customer.create(
                UUID.randomUUID(), "Jane", "Doe", "0987654321", fingerPrintData, photo, address, gender, birthDate, userId);
        assertNotEquals(customer1, customer3);
    }

    @Test
    void testToString() {
        Customer customer = Customer.create(
                customerId, "John", "Doe", "1234567890", fingerPrintData, photo, address, gender, birthDate, userId);

        String expectedString = "Customer{" +
                "id=" + customerId +
                ", name='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", phoneNumber='" + phoneNumber + "'" +
                ", photo='" + photo + "'" +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", userId=" + userId +
                '}';

        assertEquals(expectedString, customer.toString());
    }
}
