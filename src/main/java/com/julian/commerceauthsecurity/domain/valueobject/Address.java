package com.julian.commerceauthsecurity.domain.valueobject;

import java.util.Objects;

public final class Address {
    private final String street;
    private final String houseNumber;
    private final String floor;
    private final String door;

    private Address(String street, String houseNumber, String floor, String door) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        this.street = street;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.door = door;
    }

    public static Address create(String street, String houseNumber, String floor, String door) {
        return new Address(street, houseNumber, floor, door);
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

    @Override
    public String toString() {
        return "Address{street='" + this.street + "', houseNumber='" + this.houseNumber + "', floor='" + this.floor + "', door='" + this.door + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            return Objects.equals(this.toString(), o.toString());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.toString());
    }
}