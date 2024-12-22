package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class PhoneNumber extends AbstractValueObject<String> {

    private PhoneNumber(String value) {
        super(value);
    }

    public static PhoneNumber create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (!value.matches("^\\+?[0-9]{1,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        return new PhoneNumber(value);
    }
}
