package com.julian.commerceauthsecurity.domain.valueobject;

import com.julian.commerceshared.valueobject.AbstractValueObject;

public class Password extends AbstractValueObject<String> {

    private Password(String value) {
        super(value);
    }

    public static Password create(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        return new Password(value);
    }

    public static boolean isValidFormat(String value) {
        if(!value.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$")) {
            throw new IllegalArgumentException("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character");
        }
        return true;
    }
}
