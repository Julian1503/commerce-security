package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testCreateValidAddress() {
        Address address = Address.create("Main Street", "123", "4A", "2B");
        assertNotNull(address);
        assertEquals("Main Street", address.getStreet());
        assertEquals("123", address.getHouseNumber());
        assertEquals("4A", address.getFloor());
        assertEquals("2B", address.getDoor());
    }

    @Test
    void testCreateAddressWithNullStreet() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Address.create(null, "123", "4A", "2B"));
        assertEquals("Street cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateAddressWithEmptyStreet() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Address.create("", "123", "4A", "2B");
        });
        assertEquals("Street cannot be null or empty", exception.getMessage());
    }

    @Test
    void testAddressEqualityAndHashCode() {
        Address address1 = Address.create("Main Street", "123", "4A", "2B");
        Address address2 = Address.create("Main Street", "123", "4A", "2B");
        Address address3 = Address.create("Another Street", "456", "1B", "3C");

        assertEquals(address1, address2);
        assertNotEquals(address1, address3);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }

    @Test
    void testAddressEquality_False() {
        Address address1 = Address.create("Main Street", "123", "4A", "2B");
        Address address2 = Address.create("Main Street", "124", "4A", "2C");

        boolean result = address1.equals(address2);

        assertFalse(result);
    }

    @Test
    void testAddressEquality_ItsOwnInstance() {
        Address address = Address.create("Main Street", "123", "4A", "2B");
        boolean result = address.equals(address);
        assertTrue(result);
    }

    @Test
    void testAddressEquality_FalseWithNull() {
        Address address1 = Address.create("Main Street", "123", "4A", "2B");

        boolean result = address1.equals(null);

        assertFalse(result);
    }

    @Test
    void testAddressEquality_WithDifferentClass() {
        Address address = Address.create("Main Street", "123", "4A", "2B");
        Object otherObject = new Object();

        boolean result = address.equals(otherObject);

        assertFalse(result);
    }

    @Test
    void testAddressToString() {
        Address address = Address.create("Main Street", "123", "4A", "2B");
        String expected = "Address{street='Main Street', houseNumber='123', floor='4A', door='2B'}";
        assertEquals(expected, address.toString());
    }
}
