package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class Email extends AbstractValueObject<String> {

    private Email(String value) {
        super(value);
    }

    public static Email create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!value.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new Email(value);
    }
}
