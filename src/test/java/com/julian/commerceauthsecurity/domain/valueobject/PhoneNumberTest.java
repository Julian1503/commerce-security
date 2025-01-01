package com.julian.commerceauthsecurity.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void testCreateValidPhoneNumber() {
        PhoneNumber phoneNumber = PhoneNumber.create("+1234567890");
        assertNotNull(phoneNumber);
        assertEquals("+1234567890", phoneNumber.getValue());
    }

    @Test
    void testCreatePhoneNumberWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneNumber.create(null);
        });
        assertEquals("Phone number cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreatePhoneNumberWithEmptyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneNumber.create("");
        });
        assertEquals("Phone number cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreatePhoneNumberWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneNumber.create("invalid");
        });
        assertEquals("Invalid phone number format", exception.getMessage());
    }

    @Test
    void testPhoneNumberEqualityAndHashCode() {
        PhoneNumber phoneNumber1 = PhoneNumber.create("+1234567890");
        PhoneNumber phoneNumber2 = PhoneNumber.create("+1234567890");
        PhoneNumber phoneNumber3 = PhoneNumber.create("+0987654321");

        assertEquals(phoneNumber1, phoneNumber2);
        assertNotEquals(phoneNumber1, phoneNumber3);
        assertEquals(phoneNumber1.hashCode(), phoneNumber2.hashCode());
        assertNotEquals(phoneNumber1.hashCode(), phoneNumber3.hashCode());
    }

    @Test
    void testPhoneNumberToString() {
        PhoneNumber phoneNumber = PhoneNumber.create("+1234567890");
        assertEquals("+1234567890", phoneNumber.toString());
    }
}
