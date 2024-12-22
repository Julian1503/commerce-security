package com.julian.commerceauthsecurity.domain.valueobject;

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
}